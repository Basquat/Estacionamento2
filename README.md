<h1 align="center">🚗 Sistema de Gestão de Estacionamento</h1>

<p align="center">
Sistema fullstack para controle de fluxo de veículos e gestão de estacionamento,
desenvolvido com Java, Spring Boot, persistência em nuvem via Supabase (PostgreSQL) e frontend em HTML/React.
</p>

---

<h2 align="center">🛠️ Tecnologias Utilizadas</h2>

<div align="center">
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/java/java-original.svg" height="40" alt="Java"/>
  <img width="12"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/spring/spring-original.svg" height="40" alt="Spring Boot"/>
  <img width="12"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/hibernate/hibernate-original.svg" height="40" alt="Hibernate"/>
  <img width="12"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/postgresql/postgresql-original.svg" height="40" alt="PostgreSQL"/>
  <img width="12"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/react/react-original.svg" height="40" alt="React"/>
  <img width="12"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/git/git-original.svg" height="40" alt="Git"/>
</div>

---

<h2>📌 Visão Geral</h2>

<p>
O <strong>Sistema de Gestão de Estacionamento</strong> é uma aplicação fullstack desenvolvida para o controle de entrada,
permanência e saída de veículos, com foco em organização de domínio, integridade transacional
e boas práticas de engenharia de software.
</p>

<p>
O backend opera com <strong>persistência em nuvem</strong> via <strong>Supabase (PostgreSQL)</strong>,
utilizando <strong>Spring Data JPA</strong> com Hibernate como provedor ORM.
O frontend é uma aplicação <strong>HTML + React</strong> que consome a API REST diretamente,
sem necessidade de framework ou etapa de build.
</p>

---

<h2>🎯 Objetivos do Projeto</h2>

<ul>
  <li>Aplicar Java e Spring Boot em um sistema transacional realista</li>
  <li>Demonstrar uso prático de Spring Data JPA, Hibernate e PostgreSQL</li>
  <li>Implementar uma API REST completa com todos os verbos HTTP (GET, POST, PUT, DELETE)</li>
  <li>Integrar backend e frontend de forma desacoplada via API</li>
  <li>Servir como evidência técnica de nível júnior</li>
</ul>

---

<h2>🏗️ Arquitetura</h2>

<p>
O sistema segue uma <strong>arquitetura multicamadas</strong>, promovendo separação de responsabilidades,
facilidade de manutenção e evolução futura.
</p>

<ul>
  <li><strong>Controller Layer</strong> – Exposição de endpoints REST com Spring MVC</li>
  <li><strong>Repository Layer</strong> – Persistência de dados com Spring Data JPA</li>
  <li><strong>Model Layer</strong> – Entidades de domínio com mapeamento ORM via Hibernate e Lombok</li>
  <li><strong>Frontend</strong> – Interface em HTML + React (sem build) consumindo a API REST</li>
</ul>

---

<h2>🔌 Endpoints da API</h2>

| Método | Rota | Descrição |
|--------|------|-----------|
| `GET` | `/Automoveis` | Lista todos os veículos |
| `GET` | `/Automoveis/{id}` | Busca veículo por ID |
| `POST` | `/Automoveis/Add` | Cadastra novo veículo |
| `PUT` | `/Automoveis/{id}` | Atualiza veículo existente |
| `DELETE` | `/Automoveis/{id}` | Remove veículo |

---

<h2>💾 Persistência de Dados</h2>

<p>
A persistência é realizada em nuvem por meio do <strong>Supabase</strong>, utilizando
PostgreSQL como banco de dados e Hibernate como provedor ORM. Essa abordagem permite:
</p>

<ul>
  <li>Mapeamento objeto-relacional transparente</li>
  <li>Operações CRUD transacionais</li>
  <li>Dados persistidos em nuvem, acessíveis de qualquer ambiente</li>
  <li>Conexão via pooler do Supabase com driver PostgreSQL</li>
</ul>

---

<h2>🚀 Execução do Projeto</h2>

<ol>
  <li>Clone o repositório:
    <pre><code>git clone https://github.com/Basquat/Estacionamento2.git</code></pre>
  </li>
  <li>Acesse o diretório do projeto:
    <pre><code>cd Estacionamento2</code></pre>
  </li>
  <li>Configure o <code>application.properties</code> com suas credenciais do Supabase:
    <pre><code>spring.datasource.url=jdbc:postgresql://&lt;host&gt;:6543/postgres?prepareThreshold=0
spring.datasource.username=&lt;usuario&gt;
spring.datasource.password=&lt;senha&gt;</code></pre>
  </li>
  <li>Execute a aplicação:
    <pre><code>./mvnw spring-boot:run</code></pre>
    <p>ou</p>
    <pre><code>mvn spring-boot:run</code></pre>
  </li>
  <li>Abra o arquivo <code>index.html</code> do frontend no navegador.</li>
</ol>

---

<h2>📂 Estrutura do Projeto</h2>

<pre><code>
src/main/java
 └── basquat.estacionamento
     ├── User
     │   ├── AutoController.java
     │   ├── AutoModel.java
     │   └── AutoRepository.java
     └── EstacionamentoApplication.java

frontend
 └── index.html
</code></pre>

---

<h2>📈 Valor Técnico</h2>

<ul>
  <li>API REST completa com todos os verbos HTTP</li>
  <li>Integração real entre frontend e backend desacoplados</li>
  <li>Persistência em nuvem com PostgreSQL via Supabase</li>
  <li>Uso de Lombok para redução de boilerplate</li>
  <li>Frontend funcional sem etapa de build</li>
  <li>Base sólida para evolução futura</li>
</ul>

---

<h2>👨‍💻 Autor</h2>

<p>
João Daniel de Cerqueira Lisboa<br>
Desenvolvedor Backend em formação<br><br>

GitHub: https://github.com/Basquat <br>
LinkedIn: https://www.linkedin.com/in/jo%C3%A3o-daniel-de-cerqueira-lisboa-0184a7356
</p>
