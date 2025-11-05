# API Contatos

API simples em **Spring Boot** para gerenciamento de contatos com busca de endereço pelo **CEP** usando a API ViaCEP.

## Endpoints

* **GET** `/contatos` – Lista todos os contatos
* **GET** `/contatos/{email}` – Busca um contato pelo e-mail
* **POST** `/contatos` – Cadastra um novo contato
* **PUT** `/contatos/{email}` – Atualiza um contato existente
* **DELETE** `/contatos/{email}` – Remove um contato

## Exemplo de JSON

```json
{
  "email": "teste@gmail.com",
  "nome": "Usuário Teste",
  "telefone": "11999999999",
  "cep": "09999999"
}
```

## Como rodar

```bash
mvn spring-boot:run
```

A API estará disponível em:

```
http://localhost:8080/contatos
```
