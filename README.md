# 🏪 API de Gestão de Estoque e Catálogo de Produtos

Uma aplicação **Java Spring Boot** desenvolvida para resolver um
problema real: **auxiliar na gestão do estoque e catálogo de materiais
de uma loja**.\
O projeto foi estruturado seguindo boas práticas de desenvolvimento e
arquitetura em camadas, com foco em **manutenibilidade**, **segurança**
e **clareza de código**.

------------------------------------------------------------------------

## 🚀 Tecnologias e Ferramentas Utilizadas

-   **Java 21**
-   **Spring Boot**
-   **Spring Data JPA**
-   **Spring Security (OAuth2 e Login Social com Google)**
-   **MapStruct**
-   **Lombok**
-   **H2 / PostgreSQL**
-   **Maven**

------------------------------------------------------------------------

## 🧩 Estrutura do Projeto

O projeto segue o **padrão arquitetural em camadas**, sendo dividido em:

### **Controller Layer**

Responsável por receber e rotear as requisições HTTP.\
Chama os métodos da camada Service para processar as operações de
negócio.

### **Service Layer**

Implementa as **regras de negócio** e orquestra chamadas aos
repositórios (DAO).\
Utiliza **injeção de dependência** para reduzir acoplamento e melhorar
testabilidade.

### **Repository Layer**

Utiliza o **Spring Data JPA** para operações de persistência, abstraindo
o acesso ao banco de dados.

### **Model Layer**

Define as entidades e realiza o mapeamento com o banco de dados.

### **Security Layer**

Gerencia toda a configuração de autenticação e autorização.\
Implementa **OAuth2** e **login social com Google**, além de controle de
**ROLES** para diferentes níveis de acesso.

------------------------------------------------------------------------

## 🧠 Principais Funcionalidades

-   Cadastro e gerenciamento de **materiais/produtos**\
-   Controle de **estoque**
-   Autenticação via **OAuth2** e **Google Login**
-   Mapeamento de perfis de usuário (**roles**)
-   Retornos padronizados com **ResponseEntity**
-   **Validações personalizadas** via annotations
-   Tratamento centralizado de exceções com **Global Exception Handler**

------------------------------------------------------------------------

## 🛠️ Boas Práticas Adotadas

-   Uso de **DTOs**, **DAOs** e **injeção de dependências**
-   Conversão entre entidades e DTOs com **MapStruct + Lombok**
-   Separação clara de responsabilidades entre camadas
-   Tratamento e resposta padronizados para erros e exceções
-   **Camada de segurança robusta** e configurável

------------------------------------------------------------------------

## 🧾 Exemplos de Configuração (application.yml)

``` yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/estoque
    username: admin
    password: admin
  jpa:
    hibernate:
      ddl-auto: update
    show-sql: true

logging:
  level:
    org.springframework.security: DEBUG
    org.springframework.security.oauth2.server.authorization: DEBUG
```

------------------------------------------------------------------------

## 🔐 Segurança

A autenticação e autorização são gerenciadas pelo **Spring Security**,
com suporte a **OAuth2** e **Login Social com Google**.\
Os usuários autenticados recebem perfis de acesso diferenciados conforme
o mapeamento de **roles** definido no sistema.

------------------------------------------------------------------------

## 💡 Futuras Implementações

-   Integração com serviços externos (ex: envio de notificações)
-   Testes unitários e de integração (JUnit e Mockito)
-   Deploy automatizado com **Docker Compose**

------------------------------------------------------------------------

## 👨‍💻 Autor

**Josué Marcos Batista Fernandes**\
💼 Desenvolvedor Java \| Spring Boot \| Ruby \| Docker\
📧 <josue.fernandes@ee.ufcg.edu.br>\
🌐 [LinkedIn](https://www.linkedin.com/in/josu%C3%A9-marcos-231aa6183?lipi=urn%3Ali%3Apage%3Ad_flagship3_profile_view_base_contact_details%3BYPCw8hxuQKitetfSYIbjAQ%3D%3D)

------------------------------------------------------------------------
