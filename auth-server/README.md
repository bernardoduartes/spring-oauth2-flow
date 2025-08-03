# 🌐 Spring Authorization Server

Este projeto é um exemplo funcional de como configurar um **Authorization Server** utilizando
o [Spring Authorization Server](https://spring.io/projects/spring-authorization-server), implementando o fluxo *
*Authorization Code com PKCE** e suporte ao **OpenID Connect**, de acordo com a especificação **OAuth 2.1**.

---

## 📚 Sobre o Projeto

O Authorization Server tem como objetivo gerenciar a autenticação e autorização de clientes e usuários.
Ele expõe os principais endpoints do OAuth 2.1/OpenID Connect:

| Endpoint                            | Finalidade                                                  |
|-------------------------------------|-------------------------------------------------------------|
| `/login`                            | Autenticação de usuários                                    |
| `/oauth2/authorize`                 | Autorização de clientes                                     |
| `/oauth2/token`                     | Emissão de tokens de acesso e refresh                       |
| `/.well-known/openid-configuration` | Metadata pública de configuração do servidor OpenID Connect |
| `/oauth2/jwks`                      | Exposição das chaves públicas para validação de JWTs        |

Este projeto utiliza **armazenamento em memória** para usuários e clientes, ideal para **aprendizado, testes e
prototipagem rápida**.

---

## 🧰 Tecnologias Utilizadas

- Java 17
- Spring Boot **3.4.5**
- Spring Security **6.2.x**
- Spring Authorization Server **1.4.4**
- Maven

---

## ▶️ Como Executar o Projeto

### Pré-requisitos

- JDK 17 ou superior
- Maven 3.8+

### Passos para execução

```bash
git clone https://github.com/bernardoduartes/spring-oauth2-flow/auth-server.git
cd auth-server
mvn spring-boot:run
```

O servidor será iniciado em: `http://127.0.0.1:9000`

Você pode acessar a página de login em:  
📍 `http://127.0.0.1:9000/login`

---

## 🔑 Credenciais de Acesso

### Cliente OAuth2 de Exemplo

| Campo            | Valor                                                        |
|------------------|--------------------------------------------------------------|
| **client_id**    | `client-server`                                              |
| **secret**       | `secret`                                                     |
| **redirect_uri** | `http://127.0.0.1:8080/login/oauth2/code/client-server-oidc` |
| **scopes**       | `openid`, `profile`                                          |

> Este cliente está configurado para o **Authorization Code Flow** com OpenID Connect.

---

### Usuários Disponíveis (em memória)

| Usuário   | Senha     | Papel      |
|-----------|-----------|------------|
| `dbuser`  | `dbuser`  | `DB_USER`  |
| `apiuser` | `apiuser` | `API_USER` |

As credenciais são fornecidas em memória via `MysqlUserDetailsService`.

---

## 📁 Estrutura do Código

| Arquivo                          | Responsabilidade                                                     |
|----------------------------------|----------------------------------------------------------------------|
| `AuthorizationServerConfig.java` | Configuração do servidor de autorização (endpoints, PKCE, etc.)      |
| `ClientStoreConfig.java`         | Registro de clientes OAuth2 em memória                               |
| `MysqlUserDetailsService.java`   | Serviço de autenticação com usuários em memória                      |
| `SecurityFilterConfig.java`      | Configuração da cadeia de filtros de segurança (login, form, etc.)   |
| `TokenStoreConfig.java`          | Geração e exposição das chaves RSA usadas para assinar os tokens JWT |

---