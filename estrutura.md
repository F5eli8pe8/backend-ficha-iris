# Estrutura Geral do Banco de Dados

Este banco de dados representa um sistema de ficha de personagens para RPG, onde cada usuário pode criar e gerenciar personagens com atributos, habilidades, itens, rituais e outros dados relacionados.

---

# Tabela: usuarios

Armazena as contas das pessoas que utilizam o sistema.

## Campos

| Campo        | Tipo      | Descrição                      |
| ------------ | --------- | ------------------------------ |
| id           | UUID      | Identificador único do usuário |
| nome_usuario | VARCHAR   | Nome do usuário                |
| email        | VARCHAR   | Email da conta                 |
| senha_hash   | VARCHAR   | Senha criptografada            |
| funcao       | VARCHAR   | Função do usuário no sistema   |
| criado_em    | TIMESTAMP | Data de criação da conta       |

## Relacionamentos

- Um usuário pode possuir vários personagens.

---

# Tabela: personagens

Armazena os personagens criados pelos usuários.

## Campos

| Campo               | Tipo      | Descrição                                |
| ------------------- | --------- | ---------------------------------------- |
| id                  | UUID      | Identificador único do personagem        |
| usuario_id          | UUID      | Referência ao usuário dono do personagem |
| nome                | VARCHAR   | Nome do personagem                       |
| raca                | VARCHAR   | Raça do personagem                       |
| arquetipo           | VARCHAR   | Classe, especialização ou arquétipo      |
| imagem_url          | VARCHAR   | Link da imagem do personagem             |
| pontos_conhecimento | INT       | Quantidade de pontos de conhecimento     |
| anotacoes           | TEXT      | Observações gerais do personagem         |
| criado_em           | TIMESTAMP | Data de criação                          |
| atualizado_em       | TIMESTAMP | Data da última atualização               |

## Relacionamentos

- Cada personagem pertence a um único usuário.
- Cada personagem possui:
  - uma ficha de vida
  - uma ficha de energia
  - uma ficha de sanidade
  - uma ficha de estabilidade mental
- Cada personagem pode possuir:
  - vários atributos
  - várias habilidades
  - vários rituais
  - vários poderes
  - vários itens

---

# Tabela: vida

Controla a vida do personagem.

## Campos

| Campo         | Tipo | Descrição                |
| ------------- | ---- | ------------------------ |
| id            | UUID | Identificador único      |
| personagem_id | UUID | Referência ao personagem |
| atual         | INT  | Vida atual               |
| maximo        | INT  | Vida máxima              |

## Relacionamentos

- Cada registro pertence a um único personagem.

---

# Tabela: energia

Controla a energia do personagem.

## Campos

| Campo         | Tipo | Descrição                |
| ------------- | ---- | ------------------------ |
| id            | UUID | Identificador único      |
| personagem_id | UUID | Referência ao personagem |
| atual         | INT  | Energia atual            |
| maximo        | INT  | Energia máxima           |

## Relacionamentos

- Cada registro pertence a um único personagem.

---

# Tabela: sanidade

Controla a sanidade do personagem.

## Campos

| Campo         | Tipo | Descrição                |
| ------------- | ---- | ------------------------ |
| id            | UUID | Identificador único      |
| personagem_id | UUID | Referência ao personagem |
| atual         | INT  | Sanidade atual           |
| maximo        | INT  | Sanidade máxima          |

## Relacionamentos

- Cada registro pertence a um único personagem.

---

# Tabela: estabilidade_mental

Armazena a estabilidade mental do personagem.

## Campos

| Campo         | Tipo | Descrição                  |
| ------------- | ---- | -------------------------- |
| id            | UUID | Identificador único        |
| personagem_id | UUID | Referência ao personagem   |
| valor_atual   | INT  | Estabilidade mental atual  |
| valor_maximo  | INT  | Estabilidade mental máxima |

## Relacionamentos

- Cada registro pertence a um único personagem.

---

# Tabela: atributos

Armazena os atributos principais do personagem.

## Campos

| Campo         | Tipo    | Descrição                |
| ------------- | ------- | ------------------------ |
| id            | UUID    | Identificador único      |
| personagem_id | UUID    | Referência ao personagem |
| nome          | VARCHAR | Nome do atributo         |
| valor         | INT     | Valor do atributo        |

## Relacionamentos

- Cada atributo pertence a um personagem.
- Cada atributo pode possuir várias perícias relacionadas.

---

# Tabela: pericias

Armazena as perícias derivadas de um atributo.

## Campos

| Campo       | Tipo    | Descrição                          |
| ----------- | ------- | ---------------------------------- |
| id          | UUID    | Identificador único                |
| atributo_id | UUID    | Referência ao atributo relacionado |
| nome        | VARCHAR | Nome da perícia                    |
| valor       | INT     | Nível da perícia                   |

## Relacionamentos

- Cada perícia pertence a um atributo.

---

# Tabela: habilidades

Armazena habilidades especiais do personagem.

## Campos

| Campo         | Tipo    | Descrição                |
| ------------- | ------- | ------------------------ |
| id            | UUID    | Identificador único      |
| personagem_id | UUID    | Referência ao personagem |
| nome          | VARCHAR | Nome da habilidade       |
| descricao     | TEXT    | Descrição da habilidade  |

## Relacionamentos

- Cada habilidade pertence a um personagem.

---

# Tabela: rituais

Armazena rituais mágicos ou especiais do personagem.

## Campos

| Campo          | Tipo    | Descrição                      |
| -------------- | ------- | ------------------------------ |
| id             | UUID    | Identificador único            |
| personagem_id  | UUID    | Referência ao personagem       |
| nome           | VARCHAR | Nome do ritual                 |
| entidade       | VARCHAR | Entidade relacionada ao ritual |
| descricao      | TEXT    | Descrição do ritual            |
| custo_energia  | INT     | Custo de energia para uso      |
| custo_evolucao | INT     | Custo de evolução/aprendizado  |
| nivel          | INT     | Nível do ritual                |

## Relacionamentos

- Cada ritual pertence a um personagem.

---

# Tabela: poderes

Armazena poderes especiais do personagem.

## Campos

| Campo         | Tipo    | Descrição                        |
| ------------- | ------- | -------------------------------- |
| id            | UUID    | Identificador único              |
| personagem_id | UUID    | Referência ao personagem         |
| nome          | VARCHAR | Nome do poder                    |
| entidade      | VARCHAR | Entidade relacionada             |
| descricao     | TEXT    | Descrição do poder               |
| custo_energia | INT     | Custo de energia para utilização |

## Relacionamentos

- Cada poder pertence a um personagem.

---

# Tabela: itens

Armazena os itens do inventário do personagem.

## Campos

| Campo         | Tipo    | Descrição                |
| ------------- | ------- | ------------------------ |
| id            | UUID    | Identificador único      |
| personagem_id | UUID    | Referência ao personagem |
| nome          | VARCHAR | Nome do item             |
| descricao     | TEXT    | Descrição do item        |
| quantidade    | INT     | Quantidade possuída      |

## Relacionamentos

- Cada item pertence a um personagem.

---

# Relacionamentos Resumidos

- 1 usuário → vários personagens
- 1 personagem → 1 vida
- 1 personagem → 1 energia
- 1 personagem → 1 sanidade
- 1 personagem → 1 estabilidade mental
- 1 personagem → vários atributos
- 1 atributo → várias perícias
- 1 personagem → várias habilidades
- 1 personagem → vários rituais
- 1 personagem → vários poderes
- 1 personagem → vários itens
