<!-- =========================================================
     SISTEMA DE GESTÃO DE ESTACIONAMENTO — PROJECT README
     João Daniel Lisboa · github.com/Basquat
========================================================= -->

<h1 align="center">
  <img src="https://readme-typing-svg.demolab.com?font=JetBrains+Mono&weight=700&size=28&duration=3000&pause=1000&color=F59E0B&center=true&vCenter=true&width=700&lines=🚗+Sistema+de+Gestão+de+Estacionamento" alt="Title" />
</h1>

<p align="center">
  Sistema <strong>fullstack</strong> para controle de fluxo de veículos e gestão de estacionamento —
  backend em <strong>Java + Spring Boot</strong>, persistência em nuvem via <strong>Supabase (PostgreSQL)</strong>
  e frontend em <strong>React</strong>, consumindo a API REST de forma desacoplada.
</p>

<br/>

<p align="center">
  <img src="https://img.shields.io/badge/Java%2025-F59E0B?style=for-the-badge&logo=openjdk&logoColor=0d0f14" />
  <img src="https://img.shields.io/badge/Spring%20Boot-1a1f2e?style=for-the-badge&logo=springboot&logoColor=6EE7B7" />
  <img src="https://img.shields.io/badge/Spring%20Data%20JPA-1a1f2e?style=for-the-badge&logo=hibernate&logoColor=6EE7B7" />
  <img src="https://img.shields.io/badge/Supabase-1a1f2e?style=for-the-badge&logo=supabase&logoColor=3ECF8E" />
  <img src="https://img.shields.io/badge/PostgreSQL-1a1f2e?style=for-the-badge&logo=postgresql&logoColor=5b9cf6" />
  <img src="https://img.shields.io/badge/React-1a1f2e?style=for-the-badge&logo=react&logoColor=38BDF8" />
  <img src="https://img.shields.io/badge/Docker-1a1f2e?style=for-the-badge&logo=docker&logoColor=38BDF8" />
</p>

<p align="center">
  <img src="https://img.shields.io/badge/status-em%20desenvolvimento-F59E0B?style=flat-square&logo=github" />
  <img src="https://img.shields.io/badge/nível-júnior%20portfolio-5b9cf6?style=flat-square" />
  <img src="https://img.shields.io/badge/licença-MIT-6EE7B7?style=flat-square" />
</p>

---

## 📋 Índice

- [Visão Geral](#-visão-geral)
- [Arquitetura](#-arquitetura)
- [Tecnologias](#-tecnologias)
- [Endpoints da API](#-endpoints-da-api)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Persistência de Dados](#-persistência-de-dados)
- [Como Executar](#-como-executar)
- [Valor Técnico](#-valor-técnico)
- [Autor](#-autor)

---

## 🧭 Visão Geral

O **Sistema de Gestão de Estacionamento** é uma aplicação fullstack desenvolvida para o controle de entrada, permanência e saída de veículos, com foco em organização de domínio, integridade transacional e boas práticas de engenharia de software.

O projeto surgiu como evidência técnica prática de nível júnior — construído do zero, com decisões arquiteturais próprias, backend desenvolvido integralmente à mão e frontend entregue com o auxílio de IA como acelerador de produção.

> **Sobre o uso de IA no frontend:** meu foco era o backend. Utilizei Claude e GitHub Copilot para acelerar a entrega do frontend, reforçando conceitos de React e Tailwind sem abrir mão do entendimento de cada decisão tomada. A IA não fez o projeto — ela amplificou a velocidade de quem já sabia o que estava construindo.

---

## 🏗️ Arquitetura

O sistema segue uma **arquitetura multicamadas**, promovendo separação de responsabilidades, facilidade de manutenção e evolução futura.

```
┌─────────────────────────────────────────────────────┐
│                    FRONTEND                         │
│          React + Tailwind CSS (Estaciona+)          │
│         Sem etapa de build · HTML único             │
└─────────────────────┬───────────────────────────────┘
                      │ HTTP / REST
┌─────────────────────▼───────────────────────────────┐
│                  CONTROLLER LAYER                   │
│           Spring MVC · Endpoints REST               │
│        GET · POST · PUT · DELETE /Automoveis        │
└─────────────────────┬───────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────┐
│                  REPOSITORY LAYER                   │
│          Spring Data JPA · Hibernate ORM            │
└─────────────────────┬───────────────────────────────┘
                      │
┌─────────────────────▼───────────────────────────────┐
│              SUPABASE · PostgreSQL                  │
│         Persistência em nuvem · Pooler TCP          │
└─────────────────────────────────────────────────────┘
```

### Camadas

| Camada | Responsabilidade |
|--------|-----------------|
| **Controller** | Exposição dos endpoints REST via Spring MVC |
| **Model** | Entidades de domínio com mapeamento ORM (Hibernate + Lombok) |
| **Repository** | Persistência e consultas via Spring Data JPA |
| **Frontend** | Interface React consumindo a API REST de forma desacoplada |

---

## 🛠️ Tecnologias

<div align="center">

| Categoria | Tecnologia |
|-----------|-----------|
| **Linguagem** | Java 25 |
| **Framework** | Spring Boot |
| **Persistência** | Spring Data JPA · Hibernate |
| **Banco de Dados** | PostgreSQL (Supabase Cloud) |
| **ORM** | Hibernate |
| **Boilerplate** | Lombok |
| **Frontend** | React + Tailwind CSS |
| **Containerização** | Docker |
| **Controle de Versão** | Git / GitHub |

</div>

<br/>

<div align="center">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" height="40" alt="Java"/>
  &nbsp;&nbsp;
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" height="40" alt="Spring Boot"/>
  &nbsp;&nbsp;
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/hibernate/hibernate-original.svg" height="40" alt="Hibernate"/>
  &nbsp;&nbsp;
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original.svg" height="40" alt="PostgreSQL"/>
  &nbsp;&nbsp;
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/react/react-original.svg" height="40" alt="React"/>
  &nbsp;&nbsp;
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/docker/docker-original.svg" height="40" alt="Docker"/>
  &nbsp;&nbsp;
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/git/git-original.svg" height="40" alt="Git"/>
</div>

---

## 🔌 Endpoints da API

Base URL: `http://localhost:8080`

| Método | Rota | Descrição | Status |
|--------|------|-----------|--------|
| `GET` | `/Automoveis` | Lista todos os veículos | ✅ |
| `GET` | `/Automoveis/{id}` | Busca veículo por ID | ✅ |
| `POST` | `/Automoveis/Add` | Cadastra novo veículo | ✅ |
| `PUT` | `/Automoveis/{id}` | Atualiza veículo existente | ✅ |
| `DELETE` | `/Automoveis/{id}` | Remove veículo | ✅ |

### Exemplo de payload — `POST /Automoveis/Add`

```json
{
  "placa": "ABC-1234",
  "modelo": "Civic",
  "cor": "Prata",
  "proprietario": "João Daniel"
}
```

---

## 📂 Estrutura do Projeto

```
Estacionamento/
├── src/
│   └── main/
│       ├── java/
│       │   └── basquat.estacionamento/
│       │       ├── User/
│       │       │   ├── AutoController.java   ← endpoints REST
│       │       │   ├── AutoModel.java        ← entidade de domínio
│       │       │   └── AutoRepository.java   ← persistência JPA
│       │       └── EstacionamentoApplication.java
│       └── resources/
│           └── application.properties        ← config Supabase
├── frontend/
│   └── index.html                            ← React + Tailwind (sem build)
├── pom.xml
└── README.md
```

---

## 💾 Persistência de Dados

A persistência é realizada em nuvem por meio do **Supabase**, utilizando PostgreSQL como banco de dados e Hibernate como provedor ORM.

```
Aplicação Spring Boot
        │
        │  JDBC + driver PostgreSQL
        ▼
  Supabase Pooler (porta 6543)
        │
        ▼
  PostgreSQL (nuvem)
```

**Vantagens desta abordagem:**
- ✅ Dados persistidos em nuvem — acessíveis de qualquer ambiente
- ✅ Mapeamento objeto-relacional transparente via Hibernate
- ✅ Operações CRUD totalmente transacionais
- ✅ Zero infraestrutura local de banco de dados necessária

---

## 🚀 Como Executar

### Pré-requisitos

- Java 25+
- Maven 3.8+
- Conta no [Supabase](https://supabase.com) com projeto criado

### Passo a passo

**1. Clone o repositório**
```bash
git clone https://github.com/Basquat/Estacionamento.git
cd Estacionamento
```

**2. Configure o `application.properties`**
```properties
spring.datasource.url=jdbc:postgresql://<host>:6543/postgres?prepareThreshold=0
spring.datasource.username=<usuario>
spring.datasource.password=<senha>
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

> 💡 As credenciais ficam em **Project Settings → Database → Connection string (pooler)** no painel do Supabase.

**3. Execute a aplicação**
```bash
./mvnw spring-boot:run
# ou
mvn spring-boot:run
```

**4. Abra o frontend**

Abra o arquivo `frontend/index.html` diretamente no navegador — sem etapa de build necessária.

**5. (Opcional) Via Docker**
```bash
docker-compose up
```

---

## 📈 Valor Técnico

Este projeto demonstra na prática:

- ✅ **API REST completa** com todos os verbos HTTP (GET, POST, PUT, DELETE)
- ✅ **Arquitetura multicamadas** com separação clara de responsabilidades (Model → Controller → Repository)
- ✅ **Persistência em nuvem real** com PostgreSQL via Supabase
- ✅ **Integração frontend/backend desacoplada** — frontend consome a API sem dependência direta
- ✅ **Uso de Lombok** para redução de boilerplate e código limpo
- ✅ **Frontend funcional** entregue sem etapa de build, com estética dark e upload de foto com compressão via canvas
- ✅ **Base sólida** para evolução futura: Service Layer, autenticação, testes unitários

---

## 👨‍💻 Autor

<p align="center">
  <strong>João Daniel de Cerqueira Lisboa</strong><br/>
  <em>Engenheiro de Software Backend em formação</em>
  <br/><br/>
  <a href="https://github.com/Basquat">
    <img src="https://img.shields.io/badge/GitHub-Basquat-F59E0B?style=for-the-badge&logo=github&logoColor=0d0f14" />
  </a>
  &nbsp;
  <a href="https://www.linkedin.com/in/jo%C3%A3o-daniel-de-cerqueira-lisboa-0184a7356">
    <img src="https://img.shields.io/badge/LinkedIn-João%20Daniel-1a1f2e?style=for-the-badge&logo=linkedin&logoColor=F59E0B" />
  </a>
</p>

---

<p align="center">
  <img src="https://capsule-render.vercel.app/api?type=waving&section=footer&height=100&color=0:0d0f14,100:1a1f2e&fontColor=F59E0B&fontSize=14&text=Backend%20se%20aprende%20na%20prática%20—%20um%20commit%20de%20cada%20vez.&fontAlign=50&fontAlignY=65" />
</p>
