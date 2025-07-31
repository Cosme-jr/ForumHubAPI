# ForumHub API (Challenge Oracle ONE - Back-end Java)

Este projeto é a implementação do desafio "FórumHub" proposto pela Oracle ONE em parceria com a Alura, focado na construção de uma API RESTful completa utilizando Spring Boot.

## Funcionalidades Implementadas

* **CRUD de Tópicos:** Gerenciamento completo de tópicos, incluindo criação, listagem, busca por ID, atualização e exclusão.
* **Autenticação JWT:** Implementação de um sistema de autenticação robusto usando JSON Web Tokens (JWT) para proteger os endpoints da API.
    * Registro de usuários (via migration inicial).
    * Endpoint de login (`/login`) para geração de token JWT.
    * Filtro de segurança para validação do token JWT em todas as requisições protegidas.
    * Autorização baseada em tokens.
* **Banco de Dados:** Utilização de MySQL como banco de dados.
* **Migrations de Banco de Dados:** Gerenciamento de schema com Flyway.
* **Validação de Dados:** Validação de DTOs de entrada utilizando Jakarta Bean Validation.
* **API RESTful:** Boas práticas de design de APIs REST.

## Tecnologias Utilizadas

* **Java 17+**
* **Spring Boot 3.x**
* **Spring Data JPA**
* **Spring Security**
* **JWT (JSON Web Tokens) com Auth0 JWT**
* **MySQL**
* **Flyway Migrations**
* **Lombok**
* **Jakarta Bean Validation**
* **Maven**

## Como Rodar o Projeto

### Pré-requisitos

* JDK 17 ou superior instalado.
* Maven instalado.
* MySQL Server instalado e rodando.

### Configuração do Banco de Dados

1.  Crie um banco de dados MySQL com o nome `forumhub`.
2.  No arquivo `src/main/resources/application.properties`, configure as credenciais do seu banco de dados:
    ```properties
    spring.datasource.url=jdbc:mysql://localhost:3306/forumhub?createDatabaseIfNotExist=true&useTimezone=true&serverTimezone=America/Sao_Paulo
    spring.datasource.username=root
    spring.datasource.password=SUA_SENHA_DO_MYSQL #
    ```
    (Substitua `SUA_SENHA_DO_MYSQL` pela sua senha do MySQL.)

### Configuração do Segredo JWT

1.  Defina uma variável de ambiente chamada `JWT_SECRET_KEY` com uma string longa e complexa.
    * **No Windows:**
        ```bash
        setx JWT_SECRET_KEY "sua_chave_secreta_jwt_aqui_muito_longa_e_aleatoria"
        ```
    * **No Linux/macOS:**
        ```bash
        export JWT_SECRET_KEY="sua_chave_secreta_jwt_aqui_muito_longa_e_aleatoria"
        ```
    * **Importante:** Após definir a variável, reinicie seu terminal/IDE para que ela seja reconhecida.
2.  Certifique-se de que `application.properties` está configurado para ler esta variável:
    ```properties
    jwt.secret=${JWT_SECRET_KEY}
    jwt.expiration.minutes=60
    ```

### Rodando a Aplicação

1.  Abra o projeto na sua IDE (IntelliJ IDEA).
2.  Execute o arquivo principal da aplicação (geralmente `ForumhubApplication.java`).
    * O Flyway irá rodar as migrations automaticamente, criando as tabelas `topicos` e `usuarios` e inserindo um usuário `admin` padrão.

## Endpoints da API

A API pode ser acessada em `http://localhost:8080`.

### Autenticação

* **`POST /login`**
    * Autentica um usuário e retorna um JWT.
    * **Corpo da Requisição (JSON):**
        ```json
        {
            "login": "admin",
            "senha": "123456"
        }
        ```
    * **Resposta (JSON):**
        ```json
        {
            "tokenJWT": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJBUEkgRm9ydW1IdWIi..."
        }
        ```

### Tópicos (Requer Autenticação JWT)

Para todas as requisições abaixo, inclua o cabeçalho `Authorization: Bearer <seu_token_jwt>`.

* **`GET /topicos`**
    * Lista todos os tópicos existentes.
    * **Parâmetros (Query):** `page`, `size`, `sort`
    * **Exemplo de Resposta:**
        ```json
        {
            "totalPages": 1,
            "totalElements": 1,
            "content": [
                {
                    "id": 1,
                    "titulo": "Dúvida sobre Spring Security com JWT",
                    "mensagem": "Estou com dificuldades para configurar a autenticação JWT no meu projeto Spring Boot.",
                    "dataCriacao": "2025-07-29T23:27:03",
                    "status": "ABERTO",
                    "autor": "Nome do Aluno",
                    "curso": "Desenvolvimento Java com Spring"
                }
            ],
            // ... (outros metadados da paginação)
        }
        ```

* **`GET /topicos/{id}`**
    * Detalhes de um tópico específico por ID.
* **`POST /topicos`**
    * Cria um novo tópico.
* **`PUT /topicos`**
    * Atualiza um tópico existente.
* **`DELETE /topicos/{id}`**
    * Exclui um tópico por ID.

---

### Passo 3: Adicionar e Commitar as Alterações no Git

Agora que o `README.md` está criado e seu código está funcionando, é hora de adicionar e commitar tudo no Git.

1.  **Abra o terminal** na pasta raiz do seu projeto (ou use o terminal integrado do IntelliJ IDEA: `Alt + F12` ou `View > Tool Windows > Terminal`).

2.  **Adicione todas as alterações ao stage (área de preparação):**
    ```bash
    git add .
    ```
    Isso adicionará todos os arquivos novos e modificados (incluindo o `README.md` e as classes que você criou/modificou para JWT e segurança).

3.  **Faça um commit das alterações:**
    ```bash
    git commit -m "feat: Implementa autenticacao JWT completa e README.md"
    ```
    Você pode ajustar a mensagem de commit para algo que melhor descreva suas alterações.

### Passo 4: Subir as Alterações para o GitHub

1.  **Envie as alterações para o seu repositório remoto no GitHub:**
    ```bash
    git push origin main
    ```
    (Ou `git push origin master` se a sua branch principal for `master`).

Após a execução do `git push`, todas as suas alterações, incluindo o novo `README.md`, estarão visíveis no seu repositório GitHub. Você pode então compartilhar o link do seu repositório para a avaliação do desafio.

Me avise se tiver alguma dúvida durante esses passos!