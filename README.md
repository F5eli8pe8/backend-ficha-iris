# Ficha Iris API

API REST para gerenciamento de fichas de personagens de RPG, baseada na estrutura descrita em [estrutura.md](estrutura.md).

## Requisitos

- Java 17
- Maven 3.9+ ou o wrapper do projeto
- PostgreSQL 15+ rodando localmente

## Configuração do banco

Por padrão, a aplicação usa estas variáveis de ambiente:

- `DB_URL` com valor padrão `jdbc:postgresql://localhost:5432/ficha`
- `DB_USERNAME` com valor padrão `postgres`
- `DB_PASSWORD` com valor padrão `postgres`
- `SERVER_PORT` com valor padrão `8080`

Exemplo de banco local:

```bash
createdb ficha
```

Se preferir outro nome, ajuste `DB_URL` antes de subir a aplicação.

## Executar a aplicação

Na raiz do projeto:

```bash
./mvnw spring-boot:run
```

No Windows:

```bat
mvnw.cmd spring-boot:run
```

## Executar os testes

```bash
./mvnw test
```

No Windows:

```bat
mvnw.cmd test
```

## Documentação Swagger

Depois de subir a aplicação, acesse:

- `http://localhost:8080/swagger-ui.html`
- `http://localhost:8080/api-docs`

## Endpoints principais

Todos os recursos seguem o mesmo padrão:

- `GET /api/{recurso}` lista com paginação e filtros por query string
- `GET /api/{recurso}/{id}` busca por id
- `POST /api/{recurso}` cria um registro
- `PUT /api/{recurso}/{id}` atualiza um registro
- `DELETE /api/{recurso}/{id}` remove um registro

Recursos disponíveis:

- `/api/usuarios`
- `/api/personagens`
- `/api/vidas`
- `/api/energias`
- `/api/sanidades`
- `/api/estabilidades-mentais`
- `/api/atributos`
- `/api/pericias`
- `/api/habilidades`
- `/api/rituais`
- `/api/poderes`
- `/api/itens`

## Filtros suportados

Alguns exemplos de filtros por query string:

- `/api/usuarios?nomeUsuario=joao&email=mail.com`
- `/api/personagens?usuarioId=<uuid>&nome=ana`
- `/api/atributos?personagemId=<uuid>&nome=forca`
- `/api/pericias?atributoId=<uuid>&nome=atletismo`
- `/api/habilidades?personagemId=<uuid>`
- `/api/rituais?personagemId=<uuid>`
- `/api/poderes?personagemId=<uuid>`
- `/api/itens?personagemId=<uuid>`

## Exemplos de payload

### Usuário

```json
{
  "nomeUsuario": "usuario",
  "email": "usuario@email.com",
  "senhaHash": "hash-da-senha",
  "funcao": "ADMIN"
}
```

### Personagem

```json
{
  "usuarioId": "8d12e4f2-7d6f-4d9e-91b6-0b1f3f2ab123",
  "nome": "Ayla",
  "raca": "Humana",
  "arquetipo": "Caçadora",
  "imagemUrl": "https://exemplo.com/ayla.png",
  "pontosConhecimento": 5,
  "anotacoes": "Observações gerais"
}
```

### Vida

```json
{
  "personagemId": "8d12e4f2-7d6f-4d9e-91b6-0b1f3f2ab123",
  "atual": 10,
  "maximo": 12
}
```

## Observações

- A aplicação usa JPA com `ddl-auto=update` no perfil padrão de desenvolvimento.
- O endpoint de listagem aceita paginação do Spring Data, por exemplo `?page=0&size=10&sort=nome,asc`.
- As respostas de erro seguem um formato simples com `timestamp`, `status`, `error` e `message`.
