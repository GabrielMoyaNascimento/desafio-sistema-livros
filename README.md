Desafio Técnico: Gestão de Biblioteca com Recomendação

Este projeto é uma aplicação Full Stack de gestão de biblioteca, desenvolvida como um desafio técnico. A aplicação permite o cadastro de livros, usuários, gestão de empréstimos e devoluções, e um sistema de recomendação de livros.

-----------------------------------------------------


Backend:

Java 17

Spring Boot 3

Spring Data JPA

Spring Security (configurado para permitir acesso à API)

MySQL 8

Maven


-----------------------------------------------------


Frontend:

Angular (Arquitetura NgModule)

TypeScript

Bootstrap 5 (para estilos)

Angular CLI

-----------------------------------------------------

Testes (Backend):

JUnit 5

Mockito


-----------------------------------------------------


Pré-requisitos:

Antes de começar, garanta que tem as seguintes ferramentas instaladas:

JDK 17 ou superior.

Maven 3.8 ou superior.

Node.js 18 ou superior.

Angular CLI (npm install -g @angular/cli)

Um SGBD (Este guia usa MySQL 8).

Uma IDE (ex: IntelliJ IDEA, VS Code).


-----------------------------------------------------


1. Configuração e Execução do Backend (Spring Boot)

Assumindo que a pasta do backend se chama desafio/.

1.1. Configuração do Banco de Dados (MySQL)

Esta é a etapa mais importante.

Abra o seu cliente MySQL (como DBeaver, MySQL Workbench ou mysql -u root -p).

Crie um novo banco de dados para a aplicação. O nome utilizado nas configurações é desafio.

CREATE DATABASE desafio;


Abra o ficheiro de configuração do backend:
desafio/src/main/resources/application.properties

Localize e altere as seguintes linhas com as suas credenciais do MySQL (ou verifique se correspondem às que forneceu):

-----------------------------------------------------

Configuração do banco:


spring.application.name=desafio

spring.datasource.url=jdbc:mysql://localhost:3306/desafio

spring.datasource.username=

spring.datasource.password=

spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true

spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect


-----------------------------------------------------


1.2. Executando o Backend

Abra a pasta desafio/ na sua IDE (ex: IntelliJ IDEA).

Certifique-se de que a dependência spring-boot-starter-web está no seu pom.xml (e que a spring-boot-starter-webflux não está).

Aguarde o Maven descarregar as dependências.

Localize e execute a classe principal: src/main/java/com/desafio/desafio/DesafioApplication.java.

O servidor backend iniciará e estará disponível em http://localhost:8080.

(Alternativa via terminal: Na pasta desafio/, execute mvn spring-boot:run)


-----------------------------------------------------


2. Configuração e Execução do Frontend (Angular)

Assumindo que a pasta do frontend se chama desafio-frontend/.

2.1. Limpeza de Arquitetura (Obrigatório)

Este projeto usa a arquitetura NgModule. Ficheiros da arquitetura Standalone podem causar conflitos.

Apague os seguintes ficheiros se eles existirem:

src/app/app.config.ts

src/app/app.routes.ts

src/main.server.ts

-----------------------------------------------------

2.2. Executando o Frontend

Abra um novo terminal e navegue até à pasta do frontend:

cd caminho/para/desafio-frontend


Instale todas as dependências do Node.js (só precisa de o fazer uma vez):

npm install


Inicie o servidor de desenvolvimento do Angular:

ng serve


Abra o seu navegador e acesse a http://localhost:4200/.


-----------------------------------------------------



2.3. Sobre o Proxy (Conexão Backend <-> Frontend)

O frontend (em localhost:4200) está configurado para usar um proxy (proxy.conf.json). Isto significa que qualquer chamada à API (para /api/...) é automaticamente redirecionada para o backend (em localhost:8080), evitando problemas de CORS.

Ambos os servidores (Backend e Frontend) precisam de estar em execução ao mesmo tempo.


-----------------------------------------------------


Funcionalidades Implementadas:

Gestão de Utilizadores: CRUD completo (/usuarios).

Gestão de Livros: CRUD completo (/livros).

Gestão de Empréstimos:

Formulário para criar novos empréstimos (/emprestimos).

O dropdown de livros neste formulário filtra automaticamente, mostrando apenas livros com status "DISPONÍVEL".

Gestão de Devoluções:

Implementada diretamente na lista de livros (/livros).

Se um livro está "DISPONÍVEL", aparecem os botões "Editar/Excluir".

Se um livro está "EMPRESTADO", aparece o botão "Devolver".

Sistema de Recomendação:

Disponível em (/recomendacoes).

Usuários com histórico: Recomenda livros de categorias que já leram, mas que ainda não emprestaram.

Usuários novos (Cold Start): Recomenda livros de todas as categorias da biblioteca.

Filtro de Disponibilidade: As recomendações excluem livros que já estejam emprestados a outros utilizadores.
