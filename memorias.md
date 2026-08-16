# Memórias do Projeto Estacionamento

## Data: 2026-08-16

### Contexto Inicial
- Projeto Spring Boot 4.0.3 (Java 21) em `C:\Users\Jamile\IdeaProjects\Estacionamento`
- Frontend React 18 via CDN em `index.html` (raiz do projeto)
- Banco de dados: Supabase (PostgreSQL) - conexão pooler na porta 6543
- Backend separado do frontend, dados salvos em `localStorage`

### Alteração 1: Integração Frontend no Spring Boot
**Objetivo:** Servir o HTML pelo Spring Boot para ter um único projeto deployável no Render.

**Arquivos modificados:**
- `index.html` movido de `/` para `src/main/resources/static/index.html`
- Frontend alterado para consumir API REST (`/Automoveis`) ao invés de `localStorage`
- `application.properties`: adicionadas variáveis de ambiente `${DATABASE_URL:...}` para deploy no Render

**Arquivos Java modificados:**
- `AutoModel.java`: removidos enums e Lombok, adicionados getters/setters manuais
- `AutoController.java`: mantido como estava (funcionando)
- `AutoRepository.java`: mantido como estava

**Problema encontrado:** Projeto não compilava porque não havia JDK 21 instalado. Solução: usar JDK do IntelliJ (`C:\Program Files\JetBrains\IntelliJ IDEA 2026.1\jbr`) via `JAVA_HOME`.

**Arquivo SQL inicial do Supabase:**
```sql
create database estacionamento;
DROP TABLE IF EXISTS auto_model;
DROP TABLE IF EXISTS automodel;
CREATE TABLE automodel (
    automoveisid INTEGER GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    automovel VARCHAR(50),
    pago BOOLEAN DEFAULT FALSE,
    metodopagamento VARCHAR(50),
    placa VARCHAR(20),
    valor INTEGER
);
ALTER TABLE automodel ENABLE ROW LEVEL SECURITY;
CREATE POLICY "Public Access" ON automodel FOR ALL USING (true) WITH CHECK (true);
```

### Alteração 2: Remoção do Lombok
**Motivo:** Causava erro de compilação (`cannot find symbol getPlaca()` etc) porque o Lombok não estava processando as anotações corretamente no ambiente.

**Arquivos modificados:**
- `AutoModel.java`: removida anotação `@Data`, adicionados todos os getters/setters manualmente
- `pom.xml`: removida dependência `org.projectlombok:lombok`

### Alteração 3: Feature de Múltiplos Pagamentos
**Objetivo:** Permitir que um mesmo veículo tenha múltiplas formas de pagamento (ex: R$ 20 dinheiro + R$ 5 Pix = R$ 25 total).

**Arquivos criados:**
- `Pagamento.java` (`src/main/java/basquat/estacionamento/User/Pagamento.java`): entidade JPA com campos `id`, `autoId`, `metodoPagamento`, `valor`, `data`
- `PagamentoRepository.java`: interface JPA com métodos `findByAutoId` e `deleteByAutoId`
- `PagamentoController.java`: REST controller em `/Automoveis/{id}/pagamentos` com endpoints GET, POST, DELETE

**Arquivos modificados:**
- `AutoModel.java`: removido campo `metodoPagamento` (agora é da entidade `Pagamento`)
- `AutoController.java`: removida lógica de `metodoPagamento` do PUT
- `AutoRepository.java`: removido método `placa(String)` que não fazia sentido, mantido `existsByPlacaIgnoreCase`

**Arquivo SQL atualizado necessário no Supabase:**
```sql
-- Criar banco se não existir
CREATE DATABASE IF NOT EXISTS estacionamento;

-- Remover tabelas antigas apenas se necessário (cuidado: apaga dados!)
-- DROP TABLE IF EXISTS pagamento;
-- DROP TABLE IF EXISTS auto_model;

-- Criar tabela de veículos (se não existir)
CREATE TABLE IF NOT EXISTS auto_model (
    id VARCHAR(50) PRIMARY KEY,
    placa VARCHAR(20) NOT NULL UNIQUE,
    tipo VARCHAR(20) NOT NULL,
    valor DOUBLE PRECISION NOT NULL,
    pago BOOLEAN NOT NULL DEFAULT FALSE,
    entrada BIGINT NOT NULL
);

-- Criar tabela de pagamentos (se não existir)
CREATE TABLE IF NOT EXISTS pagamento (
    id VARCHAR(50) PRIMARY KEY,
    auto_id VARCHAR(50) NOT NULL REFERENCES auto_model(id) ON DELETE CASCADE,
    metodo_pagamento VARCHAR(20) NOT NULL,
    valor DOUBLE PRECISION NOT NULL,
    data BIGINT NOT NULL
);

-- Criar índice apenas se não existir
CREATE INDEX IF NOT EXISTS idx_pagamento_auto_id ON pagamento(auto_id);

-- Habilitar RLS (Supabase) - ignora se já existir
ALTER TABLE auto_model ENABLE ROW LEVEL SECURITY;
ALTER TABLE pagamento ENABLE ROW LEVEL SECURITY;

-- Criar políticas apenas se não existirem
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_policies WHERE tablename = 'auto_model' AND policyname = 'Public Access auto_model') THEN
        CREATE POLICY "Public Access auto_model" ON auto_model FOR ALL USING (true) WITH CHECK (true);
    END IF;
    IF NOT EXISTS (SELECT 1 FROM pg_policies WHERE tablename = 'pagamento' AND policyname = 'Public Access pagamento') THEN
        CREATE POLICY "Public Access pagamento" ON pagamento FOR ALL USING (true) WITH CHECK (true);
    END IF;
END $$;
```

### Estado Final do Frontend
- Mantém funcionalidades originais: tema claro/escuro, busca, filtros, relatórios, backup export/import
- Adicionado modal de pagamentos por veículo
- Adicionado botão "Pagamentos" em cada linha
- Cards de resumo mostram totais por método (dinheiro/pix) calculados a partir da tabela `pagamento`
- `AddForm` agora cria veículo com `pago: false` (pagamentos são adicionados separadamente)

### Como rodar localmente
1. Configurar `JAVA_HOME` para o JDK do IntelliJ: `C:\Program Files\JetBrains\IntelliJ IDEA 2026.1\jbr`
2. Executar `mvnw.cmd spring-boot:run` ou rodar `EstacionamentoApplication` pelo IntelliJ
3. Acessar `http://localhost:8080`

### Como deployar no Render
1. Subir projeto para GitHub/GitLab
2. No Render, criar Web Service conectando ao repositório
3. Configurar Environment Variables:
   - `DATABASE_URL` (ex: `jdbc:postgresql://aws-1-sa-east-1.pooler.supabase.com:6543/postgres?prepareThreshold=0`)
   - `DATABASE_USERNAME`
   - `DATABASE_PASSWORD`

### Deploy via Docker (recomendado para Render)
- `Dockerfile`: multi-stage build com Eclipse Temurin JDK 21 + JRE 21
- `.dockerignore`: ignora `.git`, `target`, `mvnw`, `.idea`, etc.
- Build automático pelo Render usando o Dockerfile

### Como configurar no Render com Docker
1. Crie um **Web Service** conectando o repositório
2. Em **Environment**, selecione **Docker**
3. Deixe **Build Command** e **Start Command** vazios (o Dockerfile controla)
4. Adicione as variáveis de ambiente:
   - `DATABASE_URL`
   - `DATABASE_USERNAME`
   - `DATABASE_PASSWORD`
   - `SPRING_PROFILES_ACTIVE` = `prod`
5. O Render detecta o `Dockerfile` automaticamente

### Testar Docker localmente
```bash
docker build -t estacionamento .
docker run -p 8080:8080 estacionamento
```

### Alteração 4: Atualizações em Tempo Real via SSE
**Objetivo:** Quando um usuário adiciona, apaga ou atualiza um veículo (ou gerencia pagamentos), todos os usuários conectados veem a atualização imediatamente sem precisar dar refresh (F5).

**Tecnologia:** Server-Sent Events (SSE) — nativo no Spring Boot via `SseEmitter`, sem dependências adicionais.

**Arquivos criados:**
- `EventoService.java` (`src/main/java/basquat/estacionamento/User/EventoService.java`): serviço Spring que gerencia uma lista thread-safe (`CopyOnWriteArrayList`) de `SseEmitter` e expõe o método `notificar()` que faz broadcast de um evento `atualizacao` para todos os emitters conectados.
- `EventoController.java` (`src/main/java/basquat/estacionamento/User/EventoController.java`): controller REST em `GET /eventos` que retorna um `SseEmitter` (produz `text/event-stream`).

**Arquivos modificados:**
- `AutoController.java`: injetado `EventoService` via `@Autowired`; chamado `eventoService.notificar()` após POST (addCarro), PUT (putModel) e DELETE (deleteAuto). O fluxo de retorno foi preservado — o método salva, notifica, depois retorna o objeto salvo.
- `PagamentoController.java`: injetado `EventoService` via `@Autowired`; chamado `eventoService.notificar()` após POST (addPagamento), DELETE por ID (deletePagamento) e DELETE em massa (deleteAllPagamentos).
- `index.html` (frontend): adicionado `useEffect` com `EventSource('/eventos')` que escuta eventos `atualizacao` e chama `loadItems()` automaticamente. O `EventSource` reconecta sozinho em caso de queda. Cleanup com `src.close()` no unmount.

**Como funciona:**
1. Cliente abre conexão SSE em `GET /eventos` (mantida viva pelo servidor).
2. Qualquer mutation (add/edit/delete de veículo ou pagamento) dispara `eventoService.notificar()`.
3. O broadcast envia um evento `atualizacao` para todos os emitters conectados.
4. Cada cliente recebe o evento e recarrega a lista via `loadItems()`.

**Considerações:**
- Zero impacto nas rotas existentes — o endpoint `/eventos` é independente de `/Automoveis`.
- Nenhuma alteração no `pom.xml` — SSE é nativo do Spring Web MVC.
- Conexões são automaticamente removidas da lista quando o cliente desconecta (`onCompletion` / `onTimeout`).

### Alteração 5: Endpoint /health para manter o servidor Render ativo
**Objetivo:** O Render (plano gratuito) dorme após 15 minutos sem requisições. O endpoint `/health` permite que um serviço de pinger externo envie requisições periódicas para manter o servidor ativo.

**Arquivo criado:**
- `HealthController.java` (`src/main/java/basquat/estacionamento/User/HealthController.java`): controller REST com `GET /health` retornando `{"status": "ok", "timestamp": <unix>}`.

**Como manter o servidor ativo no Render:**
1. Configurar um pinger externo (ex: [UptimeRobot](https://uptimerobot.com), gratuito) para fazer `GET /health` a cada 5 minutos.
2. Alternativa: usar o recurso **Health Check** do próprio Render — no dashboard do Web Service, em *Settings → Health Check*, apontar para `GET /health`.
3. O endpoint também serve como verificação de que a aplicação subiu corretamente após deploys.

### Alteração 6: Breakdown de pagamentos no Resumo
**Objetivo:** O Resumo (seção "Resumo" no topo) agora mostra a separação por dinheiro e pix para carros e motos, mesmo quando há múltiplas formas de pagamento. O Relatório já mostrava essa divisão; o Resumo não.

**Arquivo modificado:**
- `index.html` (frontend): 
  - Adicionada função `countPagamentos(lista, metodo)` para contar o número de pagamentos por método.
  - Adicionados 4 `StatCard` novos no grid do Resumo: "Carro: Dinheiro", "Carro: Pix", "Moto: Dinheiro", "Moto: Pix" — mostrando o valor arrecadado e a quantidade de pagamentos por método.
  - Corrigido bug: `totalCarrosPix` estava usando `totalPorMetodo(motos, 'pix')` em vez de `totalPorMetodo(carros, 'pix')` — agora usa `carros` corretamente.

**Resultados visuais:**
- Resumo agora exibe 8 cards: 4 originais (total pago, pendente) + 4 novos (dinheiro/pix por tipo de veículo).
- Relatórios mantém a exibição existente (arrecadado, dinheiro, pix, pendente, itens por tipo).
