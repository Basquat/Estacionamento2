<!-- =========================================================
     SISTEMA DE GESTÃO DE ESTACIONAMENTO — PROJECT README
     João Daniel Lisboa · github.com/Basquat
========================================================= -->

<p align="center">
  <img src="https://capsule-render.vercel.app/api?type=waving&height=220&text=🚗%20Estacionamento&fontColor=F59E0B&fontAlign=50&fontAlignY=40&fontSize=46&desc=Sistema%20Fullstack%20de%20Gestão%20de%20Veículos&descAlign=50&descAlignY=65&descSize=16&descColor=94A3B8&color=0:0d0f14,100:1a1f2e" />
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Java%2025-F59E0B?style=for-the-badge&logo=openjdk&logoColor=0d0f14" />
  <img src="https://img.shields.io/badge/Spring%20Boot-1a1f2e?style=for-the-badge&logo=springboot&logoColor=6EE7B7" />
  <img src="https://img.shields.io/badge/Spring%20Data%20JPA-1a1f2e?style=for-the-badge&logo=hibernate&logoColor=6EE7B7" />
  <img src="https://img.shields.io/badge/Supabase-1a1f2e?style=for-the-badge&logo=supabase&logoColor=3ECF8E" />
  <img src="https://img.shields.io/badge/PostgreSQL-1a1f2e?style=for-the-badge&logo=postgresql&logoColor=5b9cf6" />
  <img src="https://img.shields.io/badge/React-1a1f2e?style=for-the-badge&logo=react&logoColor=38BDF8" />
</p>

<p align="center">
  <img src="https://img.shields.io/badge/status-em%20desenvolvimento-F59E0B?style=flat-square" />
  <img src="https://img.shields.io/badge/nível-portfólio%20júnior-5b9cf6?style=flat-square" />
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

Backend desenvolvido **integralmente à mão** com Java e Spring Boot — Model, Controller e Repository com lógica de negócios própria. O frontend **(Estaciona+)** foi entregue com auxílio de IA como acelerador de produção, sem abrir mão do entendimento de cada decisão técnica tomada.

> **Sobre o frontend:** meu foco principal era o backend. Utilizei IA para acelerar a entrega do React + Tailwind, reforçando conceitos ao longo do processo. A IA não fez o projeto — ela amplificou a velocidade de quem já sabia o que estava construindo.

---

## 🏗️ Arquitetura

Arquitetura **multicamadas** com separação clara de responsabilidades:

```
┌──────────────────────────────────────────┐
│           FRONTEND (Estaciona+)          │
│       React + Tailwind CSS — sem build   │
└──────────────────┬───────────────────────┘
                   │  HTTP / REST
┌──────────────────▼───────────────────────┐
│           CONTROLLER LAYER               │
│     Spring MVC · Endpoints REST          │
└──────────────────┬───────────────────────┘
                   │
┌──────────────────▼───────────────────────┐
│           REPOSITORY LAYER               │
│     Spring Data JPA · Hibernate ORM      │
└──────────────────┬───────────────────────┘
                   │
┌──────────────────▼───────────────────────┐
│       SUPABASE · PostgreSQL (nuvem)      │
└──────────────────────────────────────────┘
```

| Camada | Responsabilidade |
|--------|-----------------|
| **Controller** | Exposição dos endpoints REST via Spring MVC |
| **Model** | Entidades de domínio mapeadas com Hibernate + Lombok |
| **Repository** | Persistência e consultas via Spring Data JPA |
| **Frontend** | Interface React consumindo a API de forma desacoplada |

---

## 🛠️ Tecnologias

<div align="center">

<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" height="40" alt="Java" title="Java 25"/>
&nbsp;&nbsp;
<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" height="40" alt="Spring Boot" title="Spring Boot"/>
&nbsp;&nbsp;
<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/hibernate/hibernate-original.svg" height="40" alt="Hibernate" title="Hibernate"/>
&nbsp;&nbsp;
<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original.svg" height="40" alt="PostgreSQL" title="PostgreSQL"/>
&nbsp;&nbsp;
<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/react/react-original.svg" height="40" alt="React" title="React"/>
&nbsp;&nbsp;
<img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/git/git-original.svg" height="40" alt="Git" title="Git"/>

</div>

---

## 🔌 Endpoints da API

**Base URL:** `http://localhost:8080`

| Método | Rota | Descrição |
|--------|------|-----------|
| `GET` | `/Automoveis` | Lista todos os veículos |
| `GET` | `/Automoveis/{id}` | Busca veículo por ID |
| `POST` | `/Automoveis/Add` | Cadastra novo veículo |
| `PUT` | `/Automoveis/{id}` | Atualiza veículo existente |
| `DELETE` | `/Automoveis/{id}` | Remove veículo |

**Exemplo de payload — `POST /Automoveis/Add`**

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
│           └── application.properties        ← configuração Supabase
├── frontend/
│   └── index.html                            ← React + Tailwind (sem build)
└── pom.xml
```

---

## 💾 Persistência de Dados

Persistência em nuvem via **Supabase**, utilizando PostgreSQL e Hibernate como provedor ORM.

```
Aplicação Spring Boot
        │
        │  JDBC · driver PostgreSQL
        ▼
Supabase Pooler  ← porta 6543
        │
        ▼
PostgreSQL (nuvem)
```

- ✅ Dados persistidos em nuvem — acessíveis de qualquer ambiente
- ✅ Mapeamento objeto-relacional via Hibernate
- ✅ Operações CRUD totalmente transacionais
- ✅ Zero infraestrutura local de banco de dados necessária

---

## 🚀 Como Executar

**Pré-requisitos:** Java 25+ · Maven 3.8+ · Conta no [Supabase](https://supabase.com)

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

> 💡 Credenciais em **Project Settings → Database → Connection string (pooler)** no painel do Supabase.

**3. Execute**
```bash
./mvnw spring-boot:run
```

**4. Abra o frontend**

Abra `frontend/index.html` diretamente no navegador — sem build necessário.

---

## 📈 Valor Técnico

- ✅ API REST completa com todos os verbos HTTP (GET, POST, PUT, DELETE)
- ✅ Arquitetura multicamadas com separação clara de responsabilidades
- ✅ Persistência em nuvem real com PostgreSQL via Supabase
- ✅ Frontend desacoplado consumindo a API REST diretamente
- ✅ Lombok para código limpo e sem boilerplate desnecessário
- ✅ Base sólida para evolução futura: Service Layer, autenticação, testes unitários

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

<p align="center">
  <img src="https://capsule-render.vercel.app/api?type=waving&section=footer&height=120&color=0:0d0f14,100:1a1f2e&fontColor=F59E0B&fontSize=14&text=Backend%20se%20aprende%20na%20prática%20—%20um%20commit%20de%20cada%20vez.&fontAlign=50&fontAlignY=65" />
</p>
