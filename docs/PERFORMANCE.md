# Investigação de performance — Render + Supabase

Data: 2026-09-02. Medições feitas do Brasil (Salvador); o Render roda nos EUA, então
lá os números de rede são ~2–3× piores.

## Medições

| O que | Tempo medido (BR) | No Render (EUA→Supabase SP) |
|---|---|---|
| Abrir conexão nova ao pooler do Supabase (TLS + auth SCRAM) | **~920 ms** | ~1.5–2 s |
| 1 query simples (round-trip) | ~65 ms | ~180 ms |
| 1 transação (query + commit) | ~120 ms | ~350 ms |
| `DELETE /Automoveis/{id}` — caminho **antigo** (~6 idas ao banco) | **270 ms** | ~1.1 s |
| `DELETE /Automoveis/{id}` — caminho **novo** (~3 idas) | **129 ms** | ~0.55 s |
| Boot do Spring até "JPA pronto" — **antes** (`ddl-auto=update`, sem flags) | ~6.7 s (só a fase JPA) | muito pior (CPU 0.1 vCPU) |
| Boot — **depois** (`ddl-auto=none` + flags de JVM + lazy-init) | ~2.5 s (fase JPA) | ~2.5× melhor |

## Diagnóstico

### 1. "Demora >10 s para rodar" = cold start do Render free
O plano **free do Render derruba o serviço após 15 min sem requisições HTTP de
entrada**. Quando alguém acessa de novo, é um cold start completo: agendar o
container + subir a JVM numa CPU de ~0.1 vCPU + contexto Spring + (antes)
introspecção de schema do Hibernate + abrir conexões ao Supabase (~1 s cada).

- O heartbeat SSE **não** evita isso (spin-down olha tráfego de *entrada*, não CPU).
- O que evita: o ping externo no `/health`. **Confirme que o UptimeRobot está de pé
  e batendo a cada 5 min** (não 15+). Sem isso, todo primeiro acesso do dia é lento.

### 2. "Apagar veículo demora a cair no servidor" = latência Render↔Supabase × nº de idas ao banco
O `DELETE` antigo fazia, em sequência: `existsById` → `findById` → carregar
pagamentos → apagar cada pagamento → apagar o veículo → commit. São ~6 idas ao
banco. Como o Render (EUA) e o Supabase (`sa-east-1`, São Paulo) estão em
continentes diferentes, **cada ida custa ~180 ms** → ~1 s só de rede, antes de
contar a CPU espremida do free tier.

### 3. Região: Render e Supabase em continentes diferentes
`DATABASE_URL` aponta para `aws-1-sa-east-1` (São Paulo). O Render free normalmente
fica em **Oregon (EUA)**. Toda query cruza o continente.

### 4. Pooler em modo transação (porta 6543) desliga o cache de prepared statements
A URL usa `?prepareThreshold=0`, obrigatório no modo transação do pgbouncer. Efeito
colateral: o Postgres **re-parseia e re-planeja toda query** — desperdício num
servidor persistente como este.

---

## O que já foi corrigido (nesta branch)

| Correção | Arquivo | Ganho |
|---|---|---|
| `DELETE` em 2 queries diretas (`DELETE ... WHERE`) em vez de carregar entidades | `AutoService.remover`, `AutoRepository`, `PagamentoRepository` | ~6 → ~3 idas ao banco (**metade do tempo**) |
| UI otimista: apagar/editar/limpar atualizam a tela **na hora**, e desfazem se o servidor recusar | `index.html` | a ação parece instantânea, independente da rede |
| `ddl-auto=none` em produção (schema gerido pelo `db/migracao.sql`) | `application-prod.properties` | remove a introspecção de schema do boot (~4–5 s + idas ao banco) |
| `hibernate.boot.allow_jdbc_metadata_access=false`, `spring.main.lazy-initialization=true` | `application-prod.properties` | contexto sobe mais rápido |
| Flags de JVM: `-XX:+UseSerialGC -XX:TieredStopAtLevel=1 -XX:MaxRAMPercentage=70` | `Dockerfile` | boot mais rápido em CPU pequena; GC não disputa a vCPU |
| Broadcast SSE assíncrono (feito antes) | `EventoService` | POST/PUT não esperam o envio aos clientes |
| `@BatchSize(200)` nos pagamentos (feito antes) | `AutoModel` | fim do N+1 no `GET /Automoveis` |

> ⚠️ Com `ddl-auto=none`, **rodar o `db/migracao.sql` no Supabase deixou de ser
> opcional** — o schema precisa estar certo antes de subir esta versão.

---

## O que depende de decisão sua (fora do código)

Em ordem de impacto:

### A. Co-localizar Render e Supabase na mesma região  ← maior ganho
Cada ida ao banco cairia de ~180 ms para ~1 ms. O `DELETE` iria a ~130 ms totais
(só o hop do usuário até o Render).
- O Render **não tem região na América do Sul**. Opções:
  1. **Recriar o projeto Supabase em `us-east-1`** (N. Virginia) e pôr o Render em
     **Virginia**. Migração é trivial (hoje são ~1 linha por tabela). Melhor combo.
  2. Manter o Supabase em São Paulo e pôr o Render em **Virginia/Ohio** (mais perto
     de SP que Oregon) — ganho parcial (~120 ms/ida em vez de ~180).

### B. Trocar para o pooler em modo sessão (porta 5432)
Servidor persistente deve usar o **session pooler**, não o transaction pooler.
Permite cache de prepared statements (queries ~20–40% mais rápidas depois do warm-up).
- No `DATABASE_URL` do Render: troque a porta **`6543` → `5432`** e **remova
  `?prepareThreshold=0`** (deixe `?sslmode=require`).
- Mantenha o pool pequeno (já está: `maximum-pool-size=5`).

### C. Acabar com o cold start
- **UptimeRobot** no `/health` a cada 5 min (grátis) — mitiga, mas o primeiro
  acesso após deploy ainda é frio.
- **Plano Starter do Render (US$ 7/mês)** — sem spin-down. É a solução definitiva
  para o ">10 s para abrir".

### D. (Opcional) Migrar o schema com Flyway
Se quiser manter `ddl-auto=none` sem depender de rodar SQL na mão, dá para colocar
o `db/migracao.sql` como `V1__init.sql` e adicionar o Flyway — ele roda as
migrações no boot automaticamente.
