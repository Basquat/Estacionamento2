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

### Ações futuras pendentes
- [ ] Corrigir backend para retornar `pagamentosMap` junto com `items` na listagem (otimização)
- [ ] Adicionar campo `data` (timestamp) na tabela `auto_model` para filtros por período
- [ ] Implementar relatório por período (dia/semana/mês)
- [ ] Adicionar histórico de pagamentos com data/hora
