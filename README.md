<h1 align="center">🚗 Sistema de Gestão de Estacionamento</h1>

<p align="center">
Sistema backend/fullstack para controle de fluxo de veículos e gestão de estacionamento,
desenvolvido com Java, Spring Boot e persistência local utilizando Spring Data JPA.
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
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/mysql/mysql-original.svg" height="40" alt="JPA / Banco Relacional"/>
  <img width="12"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/git/git-original.svg" height="40" alt="Git"/>
  <img width="12"/>
  <img src="https://cdn.jsdelivr.net/gh/devicons/devicon/icons/linux/linux-original.svg" height="40" alt="Linux"/>
</div>

---

<h2>📌 Visão Geral</h2>

<p>
O <strong>Sistema de Gestão de Estacionamento</strong> é uma aplicação desenvolvida para o controle de entrada,
permanência e saída de veículos, com foco em organização de domínio, integridade transacional
e boas práticas de engenharia de software.
</p>

<p>
O projeto foi arquitetado para operar com <strong>persistência local</strong>, utilizando
<strong>Spring Data JPA</strong>, eliminando a necessidade de servidores externos ou bancos de dados em nuvem.
Isso permite que a aplicação seja executada de forma <strong>standalone</strong> em ambiente local,
facilitando testes, demonstrações técnicas e avaliação por recrutadores.
</p>

---

<h2>🎯 Objetivos do Projeto</h2>

<ul>
  <li>Aplicar Java e Spring Boot em um sistema transacional realista</li>
  <li>Demonstrar uso prático de Spring Data JPA e Hibernate</li>
  <li>Implementar arquitetura em camadas (Controller, Service, Repository, Model)</li>
  <li>Garantir integridade e consistência dos dados</li>
  <li>Servir como evidência técnica de nível júnior</li>
</ul>

---

<h2>🏗️ Arquitetura</h2>

<p>
O sistema segue uma <strong>arquitetura multicamadas</strong>, promovendo separação de responsabilidades,
facilidade de manutenção e evolução futura.
</p>

<ul>
  <li><strong>Controller Layer</strong> – Exposição de endpoints REST</li>
  <li><strong>Service Layer</strong> – Regras de negócio e lógica transacional</li>
  <li><strong>Repository Layer</strong> – Persistência de dados com Spring Data JPA</li>
  <li><strong>Model Layer</strong> – Entidades de domínio e mapeamento ORM</li>
</ul>

---

<h2>💾 Persistência de Dados</h2>

<p>
A persistência é realizada localmente por meio do <strong>Spring Data JPA</strong>, utilizando
Hibernate como provedor ORM. Essa abordagem permite:
</p>

<ul>
  <li>Mapeamento objeto-relacional transparente</li>
  <li>Operações CRUD transacionais</li>
  <li>Independência de infraestrutura externa</li>
  <li>Execução simples em qualquer ambiente local</li>
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
  <li>Execute a aplicação:
    <pre><code>./mvnw spring-boot:run</code></pre>
    <p>ou</p>
    <pre><code>mvn spring-boot:run</code></pre>
  </li>
</ol>

<p>
A aplicação será iniciada localmente, utilizando persistência interna configurada via Spring Data JPA,
sem necessidade de banco de dados externo.
</p>

---

<h2>📂 Estrutura do Projeto</h2>

<pre><code>
src/main/java
 └── com.estacionamento
     ├── controller
     ├── service
     ├── repository
     ├── model
     └── EstacionamentoApplication.java
</code></pre>

---

<h2>📈 Valor Técnico</h2>

<ul>
  <li>Projeto orientado a boas práticas de engenharia de software</li>
  <li>Aplicação realista de backend Java</li>
  <li>Arquitetura clara e organizada</li>
  <li>Persistência local para fácil avaliação</li>
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
