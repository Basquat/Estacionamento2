# Tutorial: Gerar JAR e Deploy no Render

## 1. Preparar o projeto localmente

### 1.1. Verificar se o projeto compila
```bash
# No terminal, dentro da pasta do projeto
cd C:\Users\Jamile\IdeaProjects\Estacionamento

# Usar o JDK do IntelliJ (pois o Java 21 não está instalado globalmente)
$env:JAVA_HOME = "C:\Program Files\JetBrains\IntelliJ IDEA 2026.1\jbr"

# Limpar e compilar
.\mvnw.cmd clean compile
```

Se aparecer `BUILD SUCCESS`, está pronto.

### 1.2. Gerar o JAR
```bash
# Com o JAVA_HOME ainda configurado
.\mvnw.cmd clean package -DskipTests
```

O JAR será gerado em:
```
C:\Users\Jamile\IdeaProjects\Estacionamento\target\Estacionamento-0.0.1-SNAPSHOT.jar
```

**Importante:** O arquivo `target/` está no `.gitignore`. O Render fará o build sozinho, mas você pode testar o JAR localmente:
```bash
java -jar target\Estacionamento-0.0.1-SNAPSHOT.jar
```
Acesse `http://localhost:8080` para verificar.

---

## 2. Subir para o GitHub/GitLab

### 2.1. Inicializar git (se ainda não foi feito)
```bash
cd C:\Users\Jamile\IdeaProjects\Estacionamento
git init
git add .
git commit -m "feat: integração frontend no Spring Boot + múltiplos pagamentos"
```

### 2.2. Criar repositório no GitHub
1. Acesse https://github.com/new
2. Nome: `Estacionamento` (ou outro)
3. **NÃO** marque "Add a README file"
4. Clique em "Create repository"

### 2.3. Conectar e enviar
```bash
git remote add origin https://github.com/SEU_USUARIO/Estacionamento.git
git branch -M main
git push -u origin main
```

---

## 3. Configurar o Render

### 3.1. Criar conta no Render
1. Acesse https://render.com
2. Faça login (pode usar conta GitHub)

### 3.2. Criar Web Service
1. Clique em **"New +"** → **"Web Service"**
2. Conecte seu repositório GitHub/GitLab
3. Selecione o repositório `Estacionamento`

### 3.3. Configurações do Web Service
Preencha assim:

| Campo | Valor |
|-------|-------|
| **Name** | `estacionamento` (ou outro nome) |
| **Environment** | `Docker` → **NÃO**, use `Java` |
| **Build Command** | `./mvnw.cmd clean package -DskipTests` |
| **Start Command** | `java -jar target/Estacionamento-0.0.1-SNAPSHOT.jar` |
| **Plan** | `Free` |

### 3.4. Variáveis de Ambiente (Environment Variables)
Clique em **"Advanced"** → **"Add Environment Variable"** e adicione:

| Key | Value |
|-----|-------|
| `DATABASE_URL` | `jdbc:postgresql://aws-1-sa-east-1.pooler.supabase.com:6543/postgres?prepareThreshold=0` |
| `DATABASE_USERNAME` | `postgres.tjzwsfkgdlrsxgbyuumy` |
| `DATABASE_PASSWORD` | `estacionamento2905@` |
| `SPRING_PROFILES_ACTIVE` | `prod` |

> **Nota:** O `DATABASE_URL` deve ser a URL do Supabase (sem senha). A senha vai em `DATABASE_PASSWORD`.

### 3.5. Deploy
1. Clique em **"Create Web Service"**
2. O Render vai clonar o repositório, instalar Maven, compilar e gerar o JAR
3. O primeiro deploy demora uns 5-10 minutos
4. Quando ficar com status **"Live"**, acesse a URL fornecida (ex: `https://estacionamento.onrender.com`)

---

## 4. Configurar o Supabase

### 4.1. Acessar Supabase
1. Vá para https://supabase.com/dashboard
2. Abra seu projeto
3. Clique em **"SQL Editor"** (ícone de banco de dados)

### 4.2. Executar o SQL
1. Clique em **"New query"**
2. Cole o SQL abaixo (versão com `IF NOT EXISTS`):

```sql
-- Criar banco se não existir
CREATE DATABASE IF NOT EXISTS estacionamento;

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

-- Habilitar RLS (Supabase)
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

3. Clique em **"Run"**

### 4.3. Verificar tabelas criadas
- Clique em **"Table Editor"** (ícone de tabela)
- Você deve ver `auto_model` e `pagamento`

---

## 5. Testar a aplicação

1. Acesse a URL do Render (ex: `https://estacionamento.onrender.com`)
2. Adicione um veículo de teste
3. Clique no botão de pagamento (ícone de nota) e adicione múltiplos pagamentos
4. Verifique se os totais aparecem corretamente nos relatórios

---

## 6. Comandos úteis

### Atualizar o repositório após alterações
```bash
cd C:\Users\Jamile\IdeaProjects\Estacionamento
git add .
git commit -m "descrição da alteração"
git push
```
O Render vai detectar o push e fazer deploy automático.

### Ver logs do Render
- No dashboard do Render, clique no serviço
- Vá em **"Logs"** para ver erros em tempo real

### Forçar novo deploy
- No dashboard, clique em **"Manual Deploy"** → **"Deploy latest commit"**

---

## 7. Solução de Problemas

### Erro: "No compiler is provided in this environment"
Configure o `JAVA_HOME` antes de rodar Maven:
```bash
$env:JAVA_HOME = "C:\Program Files\JetBrains\IntelliJ IDEA 2026.1\jbr"
```

### Erro: "Cannot find symbol getPlaca()"
O Lombok não está configurado corretamente. Use a versão sem Lombok do projeto.

### Erro no Render: "Application failed to start"
Verifique os logs. Geralmente é erro de conexão com o banco.
- Confira se `DATABASE_URL`, `DATABASE_USERNAME` e `DATABASE_PASSWORD` estão corretos
- Confira se o Supabase está acessível (não está em modo manutenção)

### Erro: 500 ao acessar /Automoveis
Verifique se o SQL do Supabase foi executado corretamente e se as tabelas existem.

---

## 8. Estrutura do Projeto

```
Estacionamento/
├── pom.xml
├── memorias.md
├── src/
│   ├── main/
│   │   ├── java/basquat/estacionamento/
│   │   │   ├── EstacionamentoApplication.java
│   │   │   └── User/
│   │   │       ├── AutoModel.java
│   │   │       ├── AutoRepository.java
│   │   │       ├── AutoController.java
│   │   │       ├── Pagamento.java
│   │   │       ├── PagamentoRepository.java
│   │   │       └── PagamentoController.java
│   │   └── resources/
│   │       ├── application.properties
│   │       └── static/
│   │           └── index.html
│   └── test/
└── target/ (ignorado pelo git)
```

---

## 9. Próximos Passos

- [ ] Adicionar campo `data` na tabela `auto_model` para filtros por período
- [ ] Implementar relatório por dia/semana/mês
- [ ] Adicionar campo `operador` para identificar quem registrou o pagamento
- [ ] Implementar backup automático
