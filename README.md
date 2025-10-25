# Image Processing API

A **Image Processing API** é uma aplicação desenvolvida em **Java com Spring Boot** que oferece endpoints para **upload, transformação e recuperação de imagens**, além de um **sistema de autenticação e autorização** baseado em **Spring Security**.
O projeto foi desenvolvido utilizando apenas **recursos nativos do Java** para manipulação de imagens, garantindo leveza, portabilidade e mostrando um domínio dos recursos da linguagem.
A API pode ser executada tanto em ambiente local quanto em containers **Docker**, com suporte a variáveis de ambiente para configuração dinâmica.

---

## Índice

1. [Visão Geral](#visão-geral)
2. [Principais Tecnologias](#principais-tecnologias)
3. [Como Executar o Projeto](#como-executar-o-projeto)

   * [Execução Local (sem Docker)](#execução-local-sem-docker)
   * [Execução com Docker](#execução-com-docker)
4. [Documentação dos Endpoints](#documentação-dos-endpoints)

---

## Visão Geral

A **Image Processing API** fornece uma interface REST que permite:

* Autenticação de usuários via JWT.
* Upload de imagens para o servidor.
* Aplicação de transformações (como espelhamento, rotação, recorte, entre outras).
* Download da imagem processada.

Além disso, a API pode se integrar a serviços externos, como o **Remove.bg**, através das variáveis de ambiente configuráveis.

---

## Principais Tecnologias

* **Java 17+**
* **Spring Boot 3.x**
* **Spring Security (JWT)**
* **Spring Web**
* **Maven**
* **Docker**

---

## Como Executar o Projeto

### Execução Local (sem Docker)

1. Clone o repositório:

   ```bash
   git clone https://github.com/Cacassiano/image-processing-api.git
   cd image-processing-api
   ```

2. Execute a aplicação com o Maven:

   ```bash
   mvn spring-boot:run
   ```

3. Acesse a API:

   ```
   http://localhost:8080
   ```

---

### Execução com Docker

1. Clone o repositório:

   ```bash
   git clone https://github.com/Cacassiano/image-processing-api.git
   cd image-processing-api
   ```

2. Construa a imagem Docker:

   ```bash
   docker build -t image-processing-api .
   ```

3. Execute o container definindo as variáveis de ambiente necessárias:

   ```bash
   docker run -p 8080:8080 \
     -e SECRET_KEY=chave_secreta_jwt \
     -e REMOVE_BG_API_KEY=sua_chave_removebg \
     -e REMOVE_BG_API_URL=https://api.remove.bg/v1.0/removebg \
     image-processing-api
   ```

4. A API estará disponível em:

   ```
   http://localhost:8080
   ```

---

## Documentação dos Endpoints

### 1. **POST /auth/register**

Cria um novo usuário no sistema.

**Request Body:**

```json
{
  "username": "usuario",
  "email":"usuario@email.com",
  "password": "senha123"
}
```

**Response:**

```json
{
  "token": "jwt_token_aqui"
}
```

---

### 2. **POST /auth/login**

Autentica um usuário e retorna um token JWT.

**Request Body:**

```json
{
  "email":"usuario@email.com",
  "password": "senha123"
}
```

**Response:**

```json
{
  "token": "jwt_token_aqui"
}
```

---

### 3. **POST /images**

Faz o upload de uma imagem e retorna o identificador da imagem armazenada.

**Headers:**

```
Authorization: Bearer <token>
Content-Type: multipart/form-data
```

**Request:**

```
image=<imagem.jpeg>
format=<jpeg>
name=<my image.jpeg>
```

**Response:**

```json
{
  "imageId": "lasidsdj-ajsdaskdj-aklsdklasd"
}
```

---

### 4. **POST /images/{image_id}/transform**

Aplica transformações à imagem informada (ex.: espelhar, rotacionar, recortar) e retorna a imagem transformada. **Angulo de rotacao é em graus**

**Headers:**

```
Authorization: Bearer <token>
Content-Type: application/json
```

**Request Body:**

```json
{
    "crop": {
        "xini": <50>,
        "yini":<50>,
        "xfin": <200>,
        "yfin":<200>
    },
    "rescale": {
        "xscale":<1.5>,
        "yscale":<1.5>
    },
    "filters": {
        "grayscale": <true>,
        "black_intesity": <-20>,
        "sepia": <true>,
        "sepia_saturation": <50>,
        "remove_background":<true>
    },
    "rotation": <85>,
    "output":<"png">
}
```

**Response:**
Retorna a imagem processada em formato binário (`image/jpeg` ou `image/png`).

---

### 5. **GET /images/{image_id}**

Recupera a imagem armazenada a partir do seu identificador.

**Headers:**

```
Authorization: Bearer <token>
```

**Response:**
Retorna a imagem original armazenada no servidor.
