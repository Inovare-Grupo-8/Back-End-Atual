# Documentação de Endpoints do Sistema IMA-API

Esta documentação contém todos os endpoints disponíveis no sistema, organizados por controlador, com descrição, requisitos, validações e exemplos de chamadas cURL para cada um.

## Swagger UI

O sistema disponibiliza uma interface Swagger para teste interativo dos endpoints:

**URL do Swagger:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

### Desativar Redirecionamento OAuth

Para acessar o Swagger sem redirecionamento para autenticação do Google, é necessário desativar temporariamente a configuração OAuth no arquivo de configuração de segurança do projeto.

## Índice
1. [UsuarioController](#usuariocontroller)
2. [AssistenteSocialController](#assistentesocialcontroller)
3. [ConsultaController](#consultacontroller)
4. [DisponibilidadeController](#disponibilidadecontroller)
5. [EnderecoController](#enderecocontroller)
6. [EspecialidadeController](#especialidadecontroller)
7. [PerfilController](#perfilcontroller)
8. [TelefoneController](#telefonecontroller)
9. [AgendaController](#agendacontroller)
10. [AvaliacaoFeedbackController](#avaliacaofeedbackcontroller)

## UsuarioController

Base URL: `/usuarios`

### Cadastro e Autenticação

| Método | Endpoint | Descrição | Requisitos | Validações |
|--------|----------|-----------|------------|------------|
| POST | `/primeira-fase` | Cadastra um usuário (primeira fase) | Body: `UsuarioInputPrimeiraFase` | Nome, sobrenome, email, CPF e senha são obrigatórios |
| POST | `/voluntario/primeira-fase` | Cadastra um voluntário (primeira fase) | Body: `UsuarioInputPrimeiraFase` | Nome, sobrenome, email, CPF e senha são obrigatórios |
| POST | `/voluntario/credenciais` | Cadastra credenciais de voluntário | Query: `email`, `nome`, `senha` | Email, nome e senha são obrigatórios |
| POST | `/segunda-fase` | Completa o cadastro de usuário (segunda fase) | Query: `idUsuario`, Body: `UsuarioInputSegundaFase` | CPF válido, nome e sobrenome obrigatórios, data de nascimento válida, gênero válido |
| POST | `/voluntario/segunda-fase` | Completa o cadastro de voluntário (segunda fase) | Query: `idUsuario`, Body: `UsuarioInputSegundaFase` | CPF válido, nome e sobrenome obrigatórios, data de nascimento válida, gênero válido |
| POST | `/autenticar` | Autentica um usuário | Body: `{ "email": "string", "senha": "string" }` | Email e senha obrigatórios |

#### Exemplos cURL

**Cadastrar usuário (primeira fase):**
```bash
curl -X POST http://localhost:8080/usuarios/primeira-fase \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João",
    "sobrenome": "Silva",
    "email": "usuario@exemplo.com",
    "cpf": "12345678900",
    "senha": "senha123"
  }'
```

**Resposta:**
```json
{
  "idUsuario": 1073741824,
  "nome": "string",
  "sobrenome": "string",
  "email": "string",
  "cpf": "string",
  "dataNascimento": "2025-10-16"
}
```

**Cadastrar voluntário (primeira fase):**
```bash
curl -X POST http://localhost:8080/usuarios/voluntario/primeira-fase \
  -H "Content-Type: application/json" \
  -d '{
    "nome": "João",
    "sobrenome": "Silva",
    "email": "voluntario@exemplo.com",
    "cpf": "12345678900",
    "senha": "senha123"
  }'
```

**Resposta:**
```json
{
  "idUsuario": 1073741824,
  "nome": "string",
  "sobrenome": "string",
  "email": "string",
  "cpf": "string",
  "dataNascimento": "2025-10-16"
}
```

**Cadastrar credenciais de voluntário:**
```bash
curl -X POST "http://localhost:8080/usuarios/voluntario/credenciais?email=voluntario@exemplo.com&nome=João&senha=senha123" \
  -H "Content-Type: application/json"
```

**Completar cadastro de usuário (segunda fase):**
```bash
curl -X POST "http://localhost:8080/usuarios/segunda-fase?idUsuario=1" \
  -H "Content-Type: application/json" \
  -d '{
    "dataNascimento": "2025-10-16",
    "rendaMinima": 0.1,
    "rendaMaxima": 0.1,
    "genero": "MASCULINO",
    "tipo": "ADMINISTRADOR",
    "endereco": {
      "cep": "12345678",
      "numero": "123",
      "complemento": "Apto 45"
    },
    "telefone": {
      "ddd": "11",
      "prefixo": "99999",
      "sufixo": "9999",
      "whatsapp": true
    },
    "funcao": "JURIDICA",
    "areaOrientacao": "string",
    "comoSoube": "string",
    "profissao": "string"
  }'
```

**Resposta:**
```json
{
  "id": 1073741824,
  "nome": "string",
  "cpf": "string",
  "email": "string",
  "senha": "string",
  "dataNascimento": "2025-10-16",
  "rendaMinima": 0.1,
  "rendaMaxima": 0.1,
  "genero": "MASCULINO",
  "tipo": "ADMINISTRADOR",
  "funcao": "JURIDICA",
  "dataCadastro": "2025-10-16T00:37:33.088Z",
  "endereco": {
    "cep": "string",
    "logradouro": "string",
    "complemento": "string",
    "numero": "string",
    "bairro": "string",
    "localidade": "string",
    "uf": "string"
  }
}
```

**Completar cadastro de voluntário (segunda fase):**
```bash
curl -X POST "http://localhost:8080/usuarios/voluntario/segunda-fase?idUsuario=1" \
  -H "Content-Type: application/json" \
  -d '{
    "dataNascimento": "2025-10-16",
    "rendaMinima": 0.1,
    "rendaMaxima": 0.1,
    "genero": "MASCULINO",
    "tipo": "ADMINISTRADOR",
    "endereco": {
      "cep": "12345678",
      "numero": "123",
      "complemento": "Apto 45"
    },
    "telefone": {
      "ddd": "11",
      "prefixo": "99999",
      "sufixo": "9999",
      "whatsapp": true
    },
    "funcao": "JURIDICA",
    "areaOrientacao": "string",
    "comoSoube": "string",
    "profissao": "string"
  }'
```

**Resposta:**
```json
{
  "id": 1073741824,
  "nome": "string",
  "cpf": "string",
  "email": "string",
  "senha": "string",
  "dataNascimento": "2025-10-16",
  "rendaMinima": 0.1,
  "rendaMaxima": 0.1,
  "genero": "MASCULINO",
  "tipo": "ADMINISTRADOR",
  "funcao": "JURIDICA",
  "dataCadastro": "2025-10-16T00:37:33.076Z",
  "endereco": {
    "cep": "string",
    "logradouro": "string",
    "complemento": "string",
    "numero": "string",
    "bairro": "string",
    "localidade": "string",
    "uf": "string"
  }
}
```

**Autenticar usuário:**
```bash
curl -X POST http://localhost:8080/usuarios/autenticar \
  -H "Content-Type: application/json" \
  -d '{
    "email": "usuario@exemplo.com",
    "senha": "senha123"
  }'
```

**Resposta:**
```json
{
  "idUsuario": 1073741824,
  "nome": "string",
  "email": "string",
  "token": "string",
  "tipo": "ADMINISTRADOR"
}
```

### Consultas

| Método | Endpoint | Descrição | Requisitos | Validações |
|--------|----------|-----------|------------|------------|
| GET | `/{id}` | Busca usuário por ID | Path: `id` do usuário | ID deve ser um número inteiro válido |
| GET | `/email/{email}` | Busca usuário por email | Path: `email` do usuário | Email deve ser válido |
| GET | `/nome/{termo}` | Busca usuário por nome | Path: `termo` de busca | Termo deve ter pelo menos 3 caracteres |
| GET | `/primeira-fase/{id}` | Busca dados da primeira fase por ID | Path: `id` do usuário | ID deve ser um número inteiro válido |
| GET | `/primeira-fase/email/{email}` | Busca dados da primeira fase por email | Path: `email` do usuário | Email deve ser válido |
| GET | `/nao-classificados` | Lista usuários não classificados | Nenhum | Nenhuma |
| GET | `/voluntarios` | Lista todos os voluntários | Nenhum | Nenhuma |
| GET | `/` | Lista todos os usuários | Nenhum | Nenhuma |

#### Exemplos cURL

**Buscar usuário por ID:**
```bash
curl -X GET http://localhost:8080/usuarios/1 \
  -H "Authorization: Bearer {seu_token_jwt}"
```

**Resposta:**
```json
{
  "id": 1073741824,
  "nome": "string",
  "cpf": "string",
  "email": "string",
  "senha": "string",
  "dataNascimento": "2025-10-16",
  "rendaMinima": 0.1,
  "rendaMaxima": 0.1,
  "genero": "MASCULINO",
  "tipo": "ADMINISTRADOR",
  "funcao": "JURIDICA",
  "dataCadastro": "2025-10-16T00:37:33.053Z",
  "endereco": {
    "cep": "string",
    "logradouro": "string",
    "complemento": "string",
    "numero": "string",
    "bairro": "string",
    "localidade": "string",
    "uf": "string"
  }
}
```

**Buscar usuário por email:**
```bash
curl -X GET http://localhost:8080/usuarios/email/usuario@exemplo.com \
  -H "Authorization: Bearer {seu_token_jwt}"
```

**Resposta:**
```json
{
  "id": 1073741824,
  "nome": "string",
  "cpf": "string",
  "email": "string",
  "senha": "string",
  "dataNascimento": "2025-10-16",
  "rendaMinima": 0.1,
  "rendaMaxima": 0.1,
  "genero": "MASCULINO",
  "tipo": "ADMINISTRADOR",
  "funcao": "JURIDICA",
  "dataCadastro": "2025-10-16T00:37:33.129Z",
  "endereco": {
    "cep": "string",
    "logradouro": "string",
    "complemento": "string",
    "numero": "string",
    "bairro": "string",
    "localidade": "string",
    "uf": "string"
  }
}
```

**Buscar usuário por nome:**
```bash
curl -X GET http://localhost:8080/usuarios/nome/joao \
  -H "Authorization: Bearer {seu_token_jwt}"
```

**Resposta:**
```json
{
  "id": 1073741824,
  "nome": "string",
  "cpf": "string",
  "email": "string",
  "senha": "string",
  "dataNascimento": "2025-10-16",
  "rendaMinima": 0.1,
  "rendaMaxima": 0.1,
  "genero": "MASCULINO",
  "tipo": "ADMINISTRADOR",
  "funcao": "JURIDICA",
  "dataCadastro": "2025-10-16T00:37:33.121Z",
  "endereco": {
    "cep": "string",
    "logradouro": "string",
    "complemento": "string",
    "numero": "string",
    "bairro": "string",
    "localidade": "string",
    "uf": "string"
  }
}
```

**Buscar usuário da primeira fase por email:**
```bash
curl -X GET http://localhost:8080/usuarios/primeira-fase/email/usuario@exemplo.com \
  -H "Authorization: Bearer {seu_token_jwt}"
```

**Resposta:**
```json
{
  "idUsuario": 1073741824,
  "nome": "string",
  "sobrenome": "string",
  "email": "string",
  "cpf": "string",
  "dataNascimento": "2025-10-16"
}
```

**Buscar usuário da primeira fase por ID:**
```bash
curl -X GET http://localhost:8080/usuarios/primeira-fase/1 \
  -H "Authorization: Bearer {seu_token_jwt}"
```

**Resposta:**
```json
{
  "idUsuario": 1073741824,
  "nome": "string",
  "sobrenome": "string",
  "email": "string",
  "cpf": "string",
  "dataNascimento": "2025-10-16"
}
```

**Listar todos os voluntários:**
```bash
curl -X GET http://localhost:8080/usuarios/voluntarios \
  -H "Authorization: Bearer {seu_token_jwt}"
```

**Resposta:**
```json
[
  {
    "idUsuario": 1073741824,
    "idVoluntario": 1073741824,
    "nome": "string",
    "sobrenome": "string",
    "email": "string",
    "funcao": "string",
    "areaOrientacao": "string",
    "dataCadastro": "2025-10-16",
    "ultimoAcesso": "2025-10-16T00:37:33.111Z",
    "ativo": true,
    "nomeCompleto": "string"
  }
]
```

**Listar usuários não classificados:**
```bash
curl -X GET http://localhost:8080/usuarios/nao-classificados \
  -H "Authorization: Bearer {seu_token_jwt}"
```

**Resposta:**
```json
[
  {
    "id": 1073741824,
    "email": "string",
    "tipo": "ADMINISTRADOR",
    "dataCadastro": "2025-10-16",
    "nome": "string",
    "sobrenome": "string",
    "cpf": "string",
    "dataNascimento": "2025-10-16",
    "rendaMinima": 0,
    "rendaMaxima": 0,
    "genero": "MASCULINO",
    "areaInteresse": "string",
    "profissao": "string",
    "logradouro": "string",
    "numero": "string",
    "complemento": "string",
    "bairro": "string",
    "cidade": "string",
    "uf": "string",
    "cep": "string",
    "telefones": [
      {
        "idTelefone": 1073741824,
        "ddd": "string",
        "prefixo": "string",
        "sufixo": "string",
        "whatsapp": true
      }
    ]
  }
]
```

### Atualizações

| Método | Endpoint | Descrição | Requisitos | Validações |
|--------|----------|-----------|------------|------------|
| PUT | `/{id}` | Atualiza dados do usuário | Path: `id` do usuário, Body: `UsuarioUpdateInput` | ID válido, dados pessoais válidos conforme regras da segunda fase |
| PATCH | `/{id}/ultimo-acesso` | Atualiza último acesso do usuário | Path: `id` do usuário | ID válido |
| POST | `/classificar/gratuidade/{id}` | Classifica usuário como gratuidade | Path: `id` do usuário | ID válido, usuário deve existir |
| POST | `/classificar/valor-social/{id}` | Classifica usuário como valor social | Path: `id` do usuário | ID válido, usuário deve existir |

#### Exemplos cURL

**Atualizar dados do usuário:**
```bash
curl -X PUT http://localhost:8080/usuarios/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {seu_token_jwt}" \
  -d '{
    "dataNascimento": "2025-10-16",
    "rendaMinima": 0.1,
    "rendaMaxima": 0.1,
    "genero": "MASCULINO",
    "tipo": "ADMINISTRADOR",
    "endereco": {
      "cep": "12345678",
      "numero": "456",
      "complemento": "Apto 45"
    },
    "telefone": {
      "ddd": "11",
      "prefixo": "99999",
      "sufixo": "9999",
      "whatsapp": true
    },
    "funcao": "JURIDICA",
    "areaOrientacao": "string",
    "comoSoube": "string",
    "profissao": "string"
  }'
```

**Resposta:**
```json
{
  "idUsuario": 1073741824,
  "nome": "string",
  "email": "string",
  "tipo": "ADMINISTRADOR"
}
```

**Atualizar último acesso:**
```bash
curl -X PATCH http://localhost:8080/usuarios/1/ultimo-acesso \
  -H "Authorization: Bearer {seu_token_jwt}"
```

**Resposta:**
```
OK
```

**Classificar usuário como gratuidade:**
```bash
curl -X POST http://localhost:8080/usuarios/classificar/gratuidade/1 \
  -H "Authorization: Bearer {seu_token_jwt}"
```

### Exclusão

| Método | Endpoint | Descrição | Requisitos | Validações |
|--------|----------|-----------|------------|------------|
| DELETE | `/{id}` | Remove um usuário | Path: `id` do usuário | ID válido, usuário deve existir |

#### Exemplos cURL

**Remover usuário:**
```bash
curl -X DELETE http://localhost:8080/usuarios/1 \
  -H "Authorization: Bearer {seu_token_jwt}"
```

**Resposta:**
```
OK
```

## AssistenteSocialController

Base URL: `/assistentes-sociais`

| Método | Endpoint | Descrição | Requisitos | Validações |
|--------|----------|-----------|------------|------------|
| GET | `/perfil` | Busca perfil do assistente social | Nenhum | Autenticação necessária |
| PUT | `/perfil` | Atualiza perfil do assistente social | Body: Dados completos do perfil | Dados válidos |
| POST | `/` | Cadastra um assistente social | Body: Dados completos do assistente social | Dados válidos |
| PATCH | `/perfil/completo` | Atualiza perfil completo do assistente social | Body: Dados completos do perfil | Dados válidos |

#### Exemplos cURL

**Buscar perfil do assistente social:**
```bash
curl -X GET http://localhost:8080/assistentes-sociais/perfil \
  -H "Authorization: Bearer {seu_token_jwt}"
```

**Resposta:**
```json
{
  "idUsuario": 1073741824,
  "nome": "string",
  "sobrenome": "string",
  "crp": "string",
  "especialidade": "string",
  "telefone": "string",
  "email": "string",
  "bio": "string",
  "fotoUrl": "string",
  "endereco": {
    "idEndereco": 1073741824,
    "cep": "string",
    "logradouro": "string",
    "complemento": "string",
    "bairro": "string",
    "numero": "string",
    "cidade": "string",
    "uf": "string",
    "criadoEm": "2025-10-16T00:41:31.433Z",
    "atualizadoEm": "2025-10-16T00:41:31.433Z",
    "versao": 1073741824
  }
}
```

**Atualizar perfil do assistente social:**
```bash
curl -X PUT http://localhost:8080/assistentes-sociais/perfil \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {seu_token_jwt}" \
  -d '{
    "nome": "string",
    "sobrenome": "string",
    "email": "string",
    "senha": "string",
    "cpf": "string",
    "dataNascimento": "string",
    "genero": "string",
    "tipo": "ADMINISTRADOR",
    "funcao": "JURIDICA",
    "profissao": "string",
    "ddd": "string",
    "numero": "string",
    "cep": "string",
    "complemento": "string",
    "crp": "string",
    "especialidade": "string",
    "telefone": "string",
    "bio": "string"
  }'
```

**Resposta:**
```json
{
  "idUsuario": 1073741824,
  "nome": "string",
  "sobrenome": "string",
  "crp": "string",
  "especialidade": "string",
  "telefone": "string",
  "email": "string",
  "bio": "string",
  "fotoUrl": "string",
  "endereco": {
    "idEndereco": 1073741824,
    "cep": "string",
    "logradouro": "string",
    "complemento": "string",
    "bairro": "string",
    "numero": "string",
    "cidade": "string",
    "uf": "string",
    "criadoEm": "2025-10-16T00:41:31.438Z",
    "atualizadoEm": "2025-10-16T00:41:31.438Z",
    "versao": 1073741824
  }
}
```

**Cadastrar assistente social:**
```bash
curl -X POST http://localhost:8080/assistentes-sociais \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {seu_token_jwt}" \
  -d '{
    "nome": "string",
    "sobrenome": "string",
    "email": "string",
    "senha": "string",
    "cpf": "string",
    "dataNascimento": "string",
    "genero": "string",
    "tipo": "ADMINISTRADOR",
    "funcao": "JURIDICA",
    "profissao": "string",
    "ddd": "string",
    "numero": "string",
    "cep": "string",
    "complemento": "string",
    "crp": "string",
    "especialidade": "string",
    "telefone": "string",
    "bio": "string"
  }'
```

**Resposta:**
```json
{
  "idUsuario": 1073741824,
  "nome": "string",
  "sobrenome": "string",
  "crp": "string",
  "especialidade": "string",
  "telefone": "string",
  "email": "string",
  "bio": "string",
  "fotoUrl": "string",
  "endereco": {
    "idEndereco": 1073741824,
    "cep": "string",
    "logradouro": "string",
    "complemento": "string",
    "bairro": "string",
    "numero": "string",
    "cidade": "string",
    "uf": "string",
    "criadoEm": "2025-10-16T00:41:31.444Z",
    "atualizadoEm": "2025-10-16T00:41:31.444Z",
    "versao": 1073741824
  }
}
```

**Atualizar perfil completo do assistente social:**
```bash
curl -X PATCH http://localhost:8080/assistentes-sociais/perfil/completo \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {seu_token_jwt}" \
  -d '{
    "nome": "string",
    "sobrenome": "string",
    "email": "string",
    "senha": "string",
    "cpf": "string",
    "dataNascimento": "string",
    "genero": "string",
    "tipo": "ADMINISTRADOR",
    "funcao": "JURIDICA",
    "profissao": "string",
    "ddd": "string",
    "numero": "string",
    "cep": "string",
    "complemento": "string",
    "crp": "string",
    "especialidade": "string",
    "telefone": "string",
    "bio": "string"
  }'
```

**Resposta:**
```json
{
  "idUsuario": 1073741824,
  "nome": "string",
  "sobrenome": "string",
  "crp": "string",
  "especialidade": "string",
  "telefone": "string",
  "email": "string",
  "bio": "string",
  "fotoUrl": "string",
  "endereco": {
    "idEndereco": 1073741824,
    "cep": "string",
    "logradouro": "string",
    "complemento": "string",
    "bairro": "string",
    "numero": "string",
    "cidade": "string",
    "uf": "string",
    "criadoEm": "2025-10-16T00:41:31.449Z",
    "atualizadoEm": "2025-10-16T00:41:31.449Z",
    "versao": 1073741824
  }
}

## ConsultaController

Base URL: `/consulta`

| Método | Endpoint | Descrição | Requisitos | Validações |
|--------|----------|-----------|------------|------------|
| POST | `/` | Agenda uma consulta | Body: `ConsultaInput` | Dados da consulta válidos |
| GET | `/{id}` | Busca consulta por ID | Path: `id` | ID deve ser um número inteiro válido |
| GET | `/usuario/{idUsuario}` | Lista consultas de um usuário | Path: `idUsuario` | ID deve ser um número inteiro válido |
| GET | `/voluntario/{idVoluntario}` | Lista consultas de um voluntário | Path: `idVoluntario` | ID deve ser um número inteiro válido |
| GET | `/status/{status}` | Lista consultas por status | Path: `status` | Status válido |
| PUT | `/{id}/status` | Atualiza status da consulta | Path: `id`, Body: `StatusConsultaInput` | ID válido, status válido |
| DELETE | `/{id}` | Cancela uma consulta | Path: `id` | ID válido |
| GET | `/horarios-disponiveis` | Lista horários disponíveis | Query: `data` (ISO `yyyy-MM-dd`), `idVoluntario` | Data válida (ISO), ID válido |
| GET | `/consultas/{id}` | Busca consulta detalhada por ID | Path: `id` | ID deve ser um número inteiro válido |
| GET | `/consultas/todas` | Lista todas as consultas detalhadas | - | - |
| GET | `/{idConsulta}/feedbacks` | Busca feedbacks por consulta | Path: `idConsulta` | ID deve ser um número inteiro válido |
| POST | `/{idConsulta}/feedbacks` | Cria feedback para consulta | Path: `idConsulta`, Body: `FeedbackInput` | ID válido, comentário válido |
| GET | `/{idConsulta}/avaliacoes` | Busca avaliações por consulta | Path: `idConsulta` | ID deve ser um número inteiro válido |
| POST | `/{idConsulta}/avaliacoes` | Cria avaliação para consulta | Path: `idConsulta`, Body: `AvaliacaoInput` | ID válido, nota válida |
| GET | `/feedbacks/{idFeedback}` | Busca feedback por ID | Path: `idFeedback` | ID deve ser um número inteiro válido |
| GET | `/avaliacoes/{idAvaliacao}` | Busca avaliação por ID | Path: `idAvaliacao` | ID deve ser um número inteiro válido |

#### Exemplos cURL

**Buscar feedbacks por consulta:**
```bash
curl -X GET http://localhost:8080/consultas/1/feedbacks \
  -H "Authorization: Bearer {seu_token_jwt}"
```

**Criar feedback para consulta:**
```bash
curl -X POST http://localhost:8080/consultas/1/feedbacks \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {seu_token_jwt}" \
  -d '{
    "comentario": "Ótimo atendimento, muito atencioso."
  }'
```

**Buscar avaliações por consulta:**
```bash
curl -X GET http://localhost:8080/consultas/1/avaliacoes \
  -H "Authorization: Bearer {seu_token_jwt}"
```

**Criar avaliação para consulta:**
```bash
curl -X POST http://localhost:8080/consultas/1/avaliacoes \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {seu_token_jwt}" \
  -d '{
    "nota": 5
  }'
```

**Listar horários disponíveis:**
```bash
curl -X GET "http://localhost:8080/consulta/horarios-disponiveis?data=2025-10-16&idVoluntario=1" \
  -H "Authorization: Bearer {seu_token_jwt}"
```

**Resposta:**
```json
{
  "data": "2025-10-16",
  "idVoluntario": 1,
  "horarios": [
    "2025-10-16T09:00:00",
    "2025-10-16T10:00:00"
  ]
}
```

**Buscar consulta detalhada por ID:**
```bash
curl -X GET http://localhost:8080/consulta/consultas/1 \
  -H "Authorization: Bearer {seu_token_jwt}"
```

**Resposta:**
```json
{
  "idConsulta": 1073741824,
  "horario": "2025-10-16T00:50:59.902Z",
  "status": "string",
  "modalidade": "string",
  "local": "string",
  "observacoes": "string",
  "idEspecialidade": 1073741824,
  "nomeEspecialidade": "string",
  "idVoluntario": 1073741824,
  "nomeVoluntario": "string",
  "idAssistido": 1073741824,
  "nomeAssistido": "string",
  "idEspecialista": 1073741824,
  "nomeEspecialista": "string",
  "idCliente": 1073741824,
  "nomeCliente": "string",
  "feedbackStatus": "string",
  "avaliacaoStatus": "string",
  "criadoEm": "2025-10-16T00:50:59.902Z",
  "atualizadoEm": "2025-10-16T00:50:59.902Z",
  "especialidade": {
    "idEspecialidade": 1073741824,
    "nome": "string",
    "criadoEm": "2025-10-16T00:50:59.902Z",
    "atualizadoEm": "2025-10-16T00:50:59.902Z",
    "versao": 1073741824
  },
  "assistido": {
    "idUsuario": 1073741824,
    "ficha": {
      "idFicha": 1073741824,
      "endereco": {
        "idEndereco": 1073741824,
        "cep": "string",
        "logradouro": "string",
        "complemento": "string",
        "bairro": "string",
        "numero": "string",
        "cidade": "string",
        "uf": "string",
        "criadoEm": "2025-10-16T00:50:59.902Z",
        "atualizadoEm": "2025-10-16T00:50:59.902Z",
        "versao": 1073741824
      },
      "nome": "string",
      "sobrenome": "string",
      "cpf": "string",
      "rendaMinima": 0,
      "rendaMaxima": 0,
      "genero": "MASCULINO",
      "dtNascim": "2025-10-16",
      "areaOrientacao": "string",
      "comoSoube": "string",
      "profissao": "string",
      "criadoEm": "2025-10-16T00:50:59.902Z",
      "atualizadoEm": "2025-10-16T00:50:59.902Z",
      "versao": 1073741824
    },
    "email": "string",
    "senha": "string",
    "tipo": "ADMINISTRADOR",
    "dataCadastro": "2025-10-16",
    "criadoEm": "2025-10-16T00:50:59.902Z",
    "atualizadoEm": "2025-10-16T00:50:59.902Z",
    "ultimoAcesso": "2025-10-16T00:50:59.902Z",
    "versao": 1073741824,
    "fotoUrl": "string",
    "classificacao": true,
    "voluntario": true
  },
  "voluntario": {
    "idUsuario": 1073741824,
    "ficha": {
      "idFicha": 1073741824,
      "endereco": {
        "idEndereco": 1073741824,
        "cep": "string",
        "logradouro": "string",
        "complemento": "string",
        "bairro": "string",
        "numero": "string",
        "cidade": "string",
        "uf": "string",
        "criadoEm": "2025-10-16T00:50:59.902Z",
        "atualizadoEm": "2025-10-16T00:50:59.902Z",
        "versao": 1073741824
      },
      "nome": "string",
      "sobrenome": "string",
      "cpf": "string",
      "rendaMinima": 0,
      "rendaMaxima": 0,
      "genero": "MASCULINO",
      "dtNascim": "2025-10-16",
      "areaOrientacao": "string",
      "comoSoube": "string",
      "profissao": "string",
      "criadoEm": "2025-10-16T00:50:59.902Z",
      "atualizadoEm": "2025-10-16T00:50:59.902Z",
      "versao": 1073741824
    },
    "email": "string",
    "senha": "string",
    "tipo": "ADMINISTRADOR",
    "dataCadastro": "2025-10-16",
    "criadoEm": "2025-10-16T00:50:59.902Z",
    "atualizadoEm": "2025-10-16T00:50:59.902Z",
    "ultimoAcesso": "2025-10-16T00:50:59.902Z",
    "versao": 1073741824,
    "fotoUrl": "string",
    "classificacao": true,
    "voluntario": true
  }
}
```

**Listar todas as consultas detalhadas:**
```bash
curl -X GET http://localhost:8080/consulta/consultas/todas \
  -H "Authorization: Bearer {seu_token_jwt}"
```

**Resposta:**
```json
[
  {
    "idConsulta": 1073741824,
    "horario": "2025-10-16T00:50:59.910Z",
    "status": "string",
    "modalidade": "string",
    "local": "string",
    "observacoes": "string",
    "idEspecialidade": 1073741824,
    "nomeEspecialidade": "string",
    "idVoluntario": 1073741824,
    "nomeVoluntario": "string",
    "idAssistido": 1073741824,
    "nomeAssistido": "string",
    "idEspecialista": 1073741824,
    "nomeEspecialista": "string",
    "idCliente": 1073741824,
    "nomeCliente": "string",
    "feedbackStatus": "string",
    "avaliacaoStatus": "string",
    "criadoEm": "2025-10-16T00:50:59.910Z",
    "atualizadoEm": "2025-10-16T00:50:59.910Z",
    "especialidade": {
      "idEspecialidade": 1073741824,
      "nome": "string",
      "criadoEm": "2025-10-16T00:50:59.910Z",
      "atualizadoEm": "2025-10-16T00:50:59.910Z",
      "versao": 1073741824
    },
    "assistido": {
      "idUsuario": 1073741824,
      "ficha": {
        "idFicha": 1073741824,
        "endereco": {
          "idEndereco": 1073741824,
          "cep": "string",
          "logradouro": "string",
          "complemento": "string",
          "bairro": "string",
          "numero": "string",
          "cidade": "string",
          "uf": "string",
          "criadoEm": "2025-10-16T00:50:59.910Z",
          "atualizadoEm": "2025-10-16T00:50:59.910Z",
          "versao": 1073741824
        },
        "nome": "string",
        "sobrenome": "string",
        "cpf": "string",
        "rendaMinima": 0,
        "rendaMaxima": 0,
        "genero": "MASCULINO",
        "dtNascim": "2025-10-16",
        "areaOrientacao": "string",
        "comoSoube": "string",
        "profissao": "string",
        "criadoEm": "2025-10-16T00:50:59.910Z",
        "atualizadoEm": "2025-10-16T00:50:59.910Z",
        "versao": 1073741824
      },
      "email": "string",
      "senha": "string",
      "tipo": "ADMINISTRADOR",
      "dataCadastro": "2025-10-16",
      "criadoEm": "2025-10-16T00:50:59.910Z",
      "atualizadoEm": "2025-10-16T00:50:59.910Z",
      "ultimoAcesso": "2025-10-16T00:50:59.910Z",
      "versao": 1073741824,
      "fotoUrl": "string",
      "classificacao": true,
      "voluntario": true
    },
    "voluntario": {
      "idUsuario": 1073741824,
      "ficha": {
        "idFicha": 1073741824,
        "endereco": {
          "idEndereco": 1073741824,
          "cep": "string",
          "logradouro": "string",
          "complemento": "string",
          "bairro": "string",
          "numero": "string",
          "cidade": "string",
          "uf": "string",
          "criadoEm": "2025-10-16T00:50:59.910Z",
          "atualizadoEm": "2025-10-16T00:50:59.910Z",
          "versao": 1073741824
        },
        "nome": "string",
        "sobrenome": "string",
        "cpf": "string",
        "rendaMinima": 0,
        "rendaMaxima": 0,
        "genero": "MASCULINO",
        "dtNascim": "2025-10-16",
        "areaOrientacao": "string",
        "comoSoube": "string",
        "profissao": "string",
        "criadoEm": "2025-10-16T00:50:59.910Z",
        "atualizadoEm": "2025-10-16T00:50:59.910Z",
        "versao": 1073741824
      },
      "email": "string",
      "senha": "string",
      "tipo": "ADMINISTRADOR",
      "dataCadastro": "2025-10-16",
      "criadoEm": "2025-10-16T00:50:59.910Z",
      "atualizadoEm": "2025-10-16T00:50:59.910Z",
      "ultimoAcesso": "2025-10-16T00:50:59.910Z",
      "versao": 1073741824,
      "fotoUrl": "string",
      "classificacao": true,
      "voluntario": true
    }
  }
]
```

## DisponibilidadeController

Base URL: `/disponibilidade`

| Método | Endpoint | Descrição | Requisitos | Validações |
|--------|----------|-----------|------------|------------|
| POST | `/` | Criar uma nova disponibilidade | Body: Dados da disponibilidade | Dados válidos |
| PATCH | `/{id}` | Atualizar uma disponibilidade existente | Path: `id` da disponibilidade, Body: Dados da disponibilidade | Dados válidos |

#### Exemplos cURL

**Criar uma nova disponibilidade:**
```bash
curl -X POST http://localhost:8080/disponibilidade \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {seu_token_jwt}" \
  -d '{
    "dataHorario": "2025-10-16T00:49:13.390Z",
    "usuarioId": 1073741824
  }'
```

**Resposta:**
```json
{
  "id": 1073741824,
  "dataHorario": "2025-10-16T00:49:13.391Z",
  "voluntarioId": 1073741824,
  "voluntarioNome": "string",
  "criadoEm": "2025-10-16T00:49:13.391Z",
  "atualizadoEm": "2025-10-16T00:49:13.391Z"
}
```

**Atualizar uma disponibilidade existente:**
```bash
curl -X PATCH http://localhost:8080/disponibilidade/1073741824 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {seu_token_jwt}" \
  -d '{
    "dataHorario": "2025-10-16T00:49:13.396Z",
    "usuarioId": 1073741824
  }'
```

**Resposta:**
```json
{
  "id": 1073741824,
  "dataHorario": "2025-10-16T00:49:13.398Z",
  "voluntarioId": 1073741824,
  "voluntarioNome": "string",
  "criadoEm": "2025-10-16T00:49:13.398Z",
  "atualizadoEm": "2025-10-16T00:49:13.398Z"
}
```

## EnderecoController

Base URL: `/enderecos`

| Método | Endpoint | Descrição | Requisitos | Validações |
|--------|----------|-----------|------------|------------|
| GET | `/{usuarioId}` | Busca endereço de um usuário | Path: `usuarioId` | ID deve ser um número inteiro válido |
| PUT | `/{usuarioId}` | Atualiza endereço de um usuário | Path: `usuarioId`, Body: `EnderecoUpdateInput` | ID válido, CEP válido |
| GET | `/cep/{cep}` | Busca endereço por CEP | Path: `cep` | CEP deve ser válido |

#### Exemplos cURL

**Buscar endereço de um usuário:**
```bash
curl -X GET http://localhost:8080/enderecos/1 \
  -H "Authorization: Bearer {seu_token_jwt}"
```

**Atualizar endereço:**
```bash
curl -X PUT http://localhost:8080/enderecos/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {seu_token_jwt}" \
  -d '{
    "cep": "01001000",
    "numero": "100",
    "complemento": "Lado ímpar"
  }'
```

**Buscar endereço por CEP:**
```bash
curl -X GET http://localhost:8080/enderecos/cep/01001000 \
  -H "Authorization: Bearer {seu_token_jwt}"
```

## EspecialidadeController

Base URL: `/especialidade`

| Método | Endpoint | Descrição | Requisitos | Validações |
|--------|----------|-----------|------------|------------|
| GET | `/{id}` | Busca especialidade por ID | Path: `id` da especialidade | ID deve ser um número inteiro válido |
| PUT | `/{id}` | Atualiza especialidade existente | Path: `id` da especialidade, Body: `{ "nome": "string" }` | ID válido, nome obrigatório |
| DELETE | `/{id}` | Remove especialidade | Path: `id` da especialidade | ID válido |
| GET | `/` | Lista todas especialidades | Nenhum | Nenhuma |
| POST | `/` | Cria nova especialidade | Body: `{ "nome": "string" }` | Nome obrigatório |

#### Exemplos cURL

**Buscar especialidade por ID:**
```bash
curl -X GET http://localhost:8080/especialidade/1 \
  -H "Authorization: Bearer {seu_token_jwt}"
```

**Resposta:**
```json
{
  "id": 1073741824,
  "nome": "string"
}
```

**Atualizar especialidade:**
```bash
curl -X PUT http://localhost:8080/especialidade/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {seu_token_jwt}" \
  -d '{
    "nome": "Nova Especialidade"
  }'
```

**Resposta:**
```json
{
  "id": 1073741824,
  "nome": "string"
}
```

**Remover especialidade:**
```bash
curl -X DELETE http://localhost:8080/especialidade/1 \
  -H "Authorization: Bearer {seu_token_jwt}"
```

**Resposta:**
```
OK
```

**Listar todas especialidades:**
```bash
curl -X GET http://localhost:8080/especialidade \
  -H "Authorization: Bearer {seu_token_jwt}"
```

**Resposta:**
```json
[
  {
    "id": 1073741824,
    "nome": "string"
  }
]
```

**Criar especialidade:**
```bash
curl -X POST http://localhost:8080/especialidade \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {seu_token_jwt}" \
  -d '{
    "nome": "Nova Especialidade"
  }'
```

**Resposta:**
```json
{
  "id": 1073741824,
  "nome": "string"
}
```

## PerfilController

Base URL: `/perfil`

| Método | Endpoint | Descrição | Requisitos | Validações |
|--------|----------|-----------|------------|------------|
| GET | `/{tipo}/endereco` | Busca endereço do perfil | Path: `tipo`, Query: `usuarioId` | Tipo e ID válidos |
| PUT | `/{tipo}/endereco` | Atualiza endereço do perfil | Path: `tipo`, Query: `usuarioId`, Body: `EnderecoUpdateInput` | Tipo e ID válidos, dados de endereço válidos |
| POST | `/{tipo}/foto` | Upload de foto do perfil | Path: `tipo`, Query: `usuarioId`, Body: `file` | Tipo e ID válidos, arquivo de imagem válido |
| GET | `/{tipo}/dados-pessoais` | Busca dados pessoais do perfil | Path: `tipo`, Query: `usuarioId` | Tipo e ID válidos |
| PATCH | `/{tipo}/dados-pessoais` | Atualiza dados pessoais do perfil | Path: `tipo`, Query: `usuarioId`, Body: `DadosPessoaisInput` | Tipo e ID válidos, dados pessoais válidos |
| POST | `/voluntario/disponibilidade` | Cadastra disponibilidade do voluntário | Query: `usuarioId`, Body: `DisponibilidadeInput` | ID válido, dados de disponibilidade válidos |
| PATCH | `/voluntario/disponibilidade` | Atualiza disponibilidade do voluntário | Query: `usuarioId`, Body: `DisponibilidadeInput` | ID válido, dados de disponibilidade válidos |
| PATCH | `/voluntario/dados-profissionais` | Atualiza dados profissionais do voluntário | Query: `usuarioId`, Body: `DadosProfissionaisInput` | ID válido, dados profissionais válidos |
| PATCH | `/assistente-social/dados-profissionais` | Atualiza dados profissionais do assistente social | Query: `usuarioId`, Body: `DadosProfissionaisInput` | ID válido, dados profissionais válidos |
| PATCH | `/assistente-social/dados-pessoais` | Atualiza dados pessoais do assistente social | Query: `usuarioId`, Body: `DadosPessoaisInput` | ID válido, dados pessoais válidos |

#### Exemplos cURL

**Buscar endereço do perfil:**
```bash
curl -X GET "http://localhost:8080/perfil/voluntario/endereco?usuarioId=1" \
  -H "Authorization: Bearer {seu_token_jwt}"
```

**Atualizar endereço do perfil:**
```bash
curl -X PUT "http://localhost:8080/perfil/voluntario/endereco?usuarioId=1" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {seu_token_jwt}" \
  -d '{
    "cep": "01001000",
    "numero": "100",
    "complemento": "Lado ímpar"
  }'
```

**Upload de foto do perfil:**
```bash
curl -X POST "http://localhost:8080/perfil/voluntario/foto?usuarioId=1" \
  -H "Content-Type: multipart/form-data" \
  -H "Authorization: Bearer {seu_token_jwt}" \
  -F "file=@/caminho/para/foto.jpg"
```

**Buscar dados pessoais do perfil:**
```bash
curl -X GET "http://localhost:8080/perfil/voluntario/dados-pessoais?usuarioId=1" \
  -H "Authorization: Bearer {seu_token_jwt}"
```

**Atualizar dados pessoais do perfil:**
```bash
curl -X PATCH "http://localhost:8080/perfil/voluntario/dados-pessoais?usuarioId=1" \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {seu_token_jwt}" \
  -d '{
    "nome": "João",
    "sobrenome": "Silva",
    "telefone": "11999999999",
    "email": "joao.silva@exemplo.com",
    "dataNascimento": "1990-01-01",
    "genero": "MASCULINO",
    "bio": "Profissional com experiência em..."
  }'
```

## TelefoneController

Base URL: `/telefones`

| Método | Endpoint | Descrição | Requisitos | Validações |
|--------|----------|-----------|------------|------------|
| GET | `/{idTelefone}` | Busca telefone por ID | Path: `idTelefone` | ID deve ser um número inteiro válido |
| PUT | `/{idTelefone}` | Atualiza telefone | Path: `idTelefone`, Body: `TelefoneInput` | ID válido, dados de telefone válidos |
| DELETE | `/{idTelefone}` | Remove telefone | Path: `idTelefone` | ID válido, telefone deve existir |
| GET | `/ficha/{idFicha}` | Busca telefones por ficha | Path: `idFicha` | ID deve ser um número inteiro válido |
| POST | `/ficha/{idFicha}` | Cadastra telefone para uma ficha | Path: `idFicha`, Body: `TelefoneInput` | ID válido, dados de telefone válidos |

#### Exemplos cURL

**Buscar telefone por ID:**
```bash
curl -X GET http://localhost:8080/telefones/1 \
  -H "Authorization: Bearer {seu_token_jwt}"
```

**Cadastrar telefone para uma ficha:**
```bash
curl -X POST http://localhost:8080/telefones/ficha/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {seu_token_jwt}" \
  -d '{
    "ddd": "11",
    "prefixo": "99999",
    "sufixo": "9999",
    "whatsapp": true
  }'
```

**Atualizar telefone:**
```bash
curl -X PUT http://localhost:8080/telefones/1 \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer {seu_token_jwt}" \
  -d '{
    "ddd": "11",
    "prefixo": "99999",
    "sufixo": "9999",
    "whatsapp": true
  }'
```

## AgendaController

Base URL: `/agenda`

| Método | Endpoint | Descrição | Requisitos | Validações |
|--------|----------|-----------|------------|------------|
| GET | `/voluntario/{idVoluntario}` | Busca agenda de um voluntário | Path: `idVoluntario`, Query: `data` (opcional) | ID válido |
| GET | `/usuario/{idUsuario}` | Busca agenda de um usuário | Path: `idUsuario`, Query: `data` (opcional) | ID válido |
| GET | `/estatisticas` | Retorna estatísticas da agenda | Nenhum | Autenticação necessária |
| GET | `/especialidade/{especialidadeId}` | Busca agenda por especialidade | Path: `especialidadeId`, Query: `page`, `size`, `sort` | ID válido |

#### Exemplos cURL

**Buscar estatísticas da agenda:**
```bash
curl -X GET http://localhost:8080/agenda/estatisticas \
  -H "Authorization: Bearer {seu_token_jwt}"
```

**Resposta:**
```json
[
  {
    "status": "string",
    "count": 9007199254740991
  }
]
```

**Buscar agenda por especialidade:**
```bash
curl -X GET "http://localhost:8080/agenda/especialidade/1?page=0&size=10&sort=horario" \
  -H "Authorization: Bearer {seu_token_jwt}"
```

**Resposta:**
```json
{
  "totalPages": 1073741824,
  "totalElements": 9007199254740991,
  "size": 1073741824,
  "content": [
    {
      "idConsulta": 1073741824,
      "horario": "2025-10-16T00:50:19.169Z",
      "status": "string",
      "modalidade": "string",
      "local": "string",
      "observacoes": "string",
      "idEspecialidade": 1073741824,
      "nomeEspecialidade": "string",
      "idVoluntario": 1073741824,
      "nomeVoluntario": "string",
      "idAssistido": 1073741824,
      "nomeAssistido": "string",
      "idEspecialista": 1073741824,
      "nomeEspecialista": "string",
      "idCliente": 1073741824,
      "nomeCliente": "string",
      "feedbackStatus": "string",
      "avaliacaoStatus": "string",
      "criadoEm": "2025-10-16T00:50:19.169Z",
      "atualizadoEm": "2025-10-16T00:50:19.169Z",
      "especialidade": {
        "idEspecialidade": 1073741824,
        "nome": "string",
        "criadoEm": "2025-10-16T00:50:19.169Z",
        "atualizadoEm": "2025-10-16T00:50:19.169Z",
        "versao": 1073741824
      },
      "assistido": {
        "idUsuario": 1073741824,
        "ficha": {
          "idFicha": 1073741824,
          "endereco": {
            "idEndereco": 1073741824,
            "cep": "string",
            "logradouro": "string",
            "complemento": "string",
            "bairro": "string",
            "numero": "string",
            "cidade": "string",
            "uf": "string",
            "criadoEm": "2025-10-16T00:50:19.169Z",
            "atualizadoEm": "2025-10-16T00:50:19.169Z",
            "versao": 1073741824
          },
          "nome": "string",
          "sobrenome": "string",
          "cpf": "string",
          "rendaMinima": 0,
          "rendaMaxima": 0,
          "genero": "MASCULINO",
          "dtNascim": "2025-10-16",
          "areaOrientacao": "string",
          "comoSoube": "string",
          "profissao": "string",
          "criadoEm": "2025-10-16T00:50:19.169Z",
          "atualizadoEm": "2025-10-16T00:50:19.169Z",
          "versao": 1073741824
        },
        "email": "string",
        "senha": "string",
        "tipo": "ADMINISTRADOR",
        "dataCadastro": "2025-10-16",
        "criadoEm": "2025-10-16T00:50:19.169Z",
        "atualizadoEm": "2025-10-16T00:50:19.169Z",
        "ultimoAcesso": "2025-10-16T00:50:19.169Z",
        "versao": 1073741824,
        "fotoUrl": "string",
        "classificacao": true,
        "voluntario": true
      },
      "voluntario": {
        "idUsuario": 1073741824,
        "ficha": {
          "idFicha": 1073741824,
          "endereco": {
            "idEndereco": 1073741824,
            "cep": "string",
            "logradouro": "string",
            "complemento": "string",
            "bairro": "string",
            "numero": "string",
            "cidade": "string",
            "uf": "string",
            "criadoEm": "2025-10-16T00:50:19.169Z",
            "atualizadoEm": "2025-10-16T00:50:19.169Z",
            "versao": 1073741824
          },
          "nome": "string",
          "sobrenome": "string",
          "cpf": "string",
          "rendaMinima": 0,
          "rendaMaxima": 0,
          "genero": "MASCULINO",
          "dtNascim": "2025-10-16",
          "areaOrientacao": "string",
          "comoSoube": "string",
          "profissao": "string",
          "criadoEm": "2025-10-16T00:50:19.169Z",
          "atualizadoEm": "2025-10-16T00:50:19.169Z",
          "versao": 1073741824
        },
        "email": "string",
        "senha": "string",
        "tipo": "ADMINISTRADOR",
        "dataCadastro": "2025-10-16",
        "criadoEm": "2025-10-16T00:50:19.169Z",
        "atualizadoEm": "2025-10-16T00:50:19.169Z",
        "ultimoAcesso": "2025-10-16T00:50:19.169Z",
        "versao": 1073741824,
        "fotoUrl": "string",
        "classificacao": true,
        "voluntario": true
      }
    }
  ],
  "number": 1073741824,
  "sort": {
    "empty": true,
    "sorted": true,
    "unsorted": true
  },
  "first": true,
  "last": true,
  "numberOfElements": 1073741824,
  "pageable": {
    "offset": 9007199254740991,
    "sort": {
      "empty": true,
      "sorted": true,
      "unsorted": true
    },
    "paged": true,
    "pageSize": 1073741824,
    "pageNumber": 1073741824,
    "unpaged": true
  },
  "empty": true
}

## AvaliacaoFeedbackController

Base URL: `/avaliacoes`

| Método | Endpoint | Descrição | Requisitos |
|--------|----------|-----------|------------|
| POST | `/consulta/{idConsulta}` | Registra avaliação para consulta | Path: `idConsulta`, Body: `AvaliacaoInput` |
| GET | `/consulta/{idConsulta}` | Busca avaliação de uma consulta | Path: `idConsulta` |
| POST | `/feedback/consulta/{idConsulta}` | Registra feedback para consulta | Path: `idConsulta`, Body: `FeedbackInput` |
| GET | `/feedback/consulta/{idConsulta}` | Busca feedback de uma consulta | Path: `idConsulta` |

---

## Estruturas de Dados Comuns

### UsuarioInput
```json
{
  "nome": "string",
  "sobrenome": "string",
  "email": "string",
  "cpf": "string",
  "senha": "string",
  "confirmacaoSenha": "string",
  "dataNascimento": "string (formato: yyyy-MM-dd)"
}
```

### UsuarioInputPrimeiraFase
```json
{
  "nome": "string",
  "sobrenome": "string",
  "email": "string",
  "cpf": "string",
  "senha": "string"
}
```

### UsuarioUpdateInput
```json
{
  "dataNascimento": "2025-10-16",
  "rendaMinima": 0.1,
  "rendaMaxima": 0.1,
  "genero": "string",
  "tipo": "ADMINISTRADOR",
  "endereco": {
    "cep": "string",
    "numero": "string",
    "complemento": "string"
  },
  "telefone": {
    "ddd": "string",
    "prefixo": "string",
    "sufixo": "string",
    "whatsapp": true
  },
  "funcao": "JURIDICA",
  "areaOrientacao": "string",
  "comoSoube": "string",
  "profissao": "string"
}
```

### UsuarioOutput
```json
{
  "id": 1073741824,
  "nome": "string",
  "cpf": "string",
  "email": "string",
  "senha": "string",
  "dataNascimento": "2025-10-16",
  "rendaMinima": 0.1,
  "rendaMaxima": 0.1,
  "genero": "MASCULINO",
  "tipo": "ADMINISTRADOR",
  "funcao": "JURIDICA",
  "dataCadastro": "2025-10-16T00:37:33.053Z",
  "endereco": {
    "cep": "string",
    "logradouro": "string",
    "complemento": "string",
    "numero": "string",
    "bairro": "string",
    "localidade": "string",
    "uf": "string"
  }
}
```

### UsuarioOutputResumido
```json
{
  "idUsuario": 1073741824,
  "nome": "string",
  "email": "string",
  "tipo": "ADMINISTRADOR"
}
```

### TelefoneInput
```json
{
  "ddd": "string",
  "prefixo": "string",
  "sufixo": "string",
  "whatsapp": boolean
}
```

### UsuarioOutputPrimeiraFase
```json
{
  "idUsuario": 1073741824,
  "nome": "string",
  "sobrenome": "string",
  "email": "string",
  "cpf": "string",
  "dataNascimento": "2025-10-16"
}
```

### EnderecoUpdateInput
```json
{
  "cep": "string",
  "numero": "string",
  "complemento": "string"
}
```

### EnderecoOutput
```json
{
  "cep": "string",
  "logradouro": "string",
  "complemento": "string",
  "numero": "string",
  "bairro": "string",
  "localidade": "string",
  "uf": "string"
}
```

### DadosPessoaisInput
```json
{
  "nome": "string",
  "sobrenome": "string",
  "telefone": "string",
  "email": "string",
  "senha": "string",
  "dataNascimento": "2025-10-16",
  "genero": "MASCULINO",
  "crp": "string",
  "bio": "string",
  "especialidade": "string"
}
```

### DadosProfissionaisInput
```json
{
  "funcao": "JURIDICA",
  "registroProfissional": "string",
  "biografiaProfissional": "string",
  "especialidade": "string",
  "especialidades": [
    "string"
  ]
}
```

### DisponibilidadeInput
```json
{
  "additionalProp1": {},
  "additionalProp2": {},
  "additionalProp3": {}
}
```

### FeedbackInput
```json
{
  "comentario": "string"
}
```

### FeedbackOutput
```json
{
  "idFeedback": 0,
  "idConsulta": 0,
  "comentario": "string",
  "dtFeedback": "2025-10-16T00:36:25.518Z"
}
```

### AvaliacaoInput
```json
{
  "nota": 0
}
```

### AvaliacaoOutput
```json
{
  "idAvaliacao": 0,
  "idConsulta": 0,
  "nota": 0,
  "dtAvaliacao": "2025-10-16T00:36:25.530Z"
}
```

### FotoUploadResponse
```json
{
  "mensagem": "string",
  "urlFoto": "string",
  "nomeArquivo": "string",
  "tamanhoArquivo": 0,
  "tipoArquivo": "string",
  "dataUpload": "2025-10-16T00:34:46.436Z",
  "sucesso": true
}
```

### UsuarioInputAtualizacaoDadosPessoais
```json
{
  "nome": "Nome",
  "sobrenome": "Sobrenome",
  "dataNascimento": "1990-01-01",
  "genero": "MASCULINO"
}
```

### EnderecoInput
```json
{
  "cep": "12345678",
  "numero": "123",
  "complemento": "Apto 45"
}
```

### TelefoneInput
```json
{
  "idUsuario": 1,
  "ddd": "11",
  "numero": "999999999",
  "tipo": "CELULAR"
}
```

### AssistenteSocialInput
```json
{
  "nome": "Nome",
  "sobrenome": "Sobrenome",
  "email": "assistente@email.com",
  "senha": "senha123",
  "cpf": "12345678900",
  "dataNascimento": "1985-01-01",
  "genero": "FEMININO",
  "crp": "12345",
  "bio": "Biografia profissional",
  "telefone": {
    "ddd": "11",
    "numero": "999999999"
  },
  "endereco": {
    "cep": "12345678",
    "logradouro": "Rua Exemplo",
    "numero": "123",
    "complemento": "Sala 10",
    "bairro": "Centro",
    "cidade": "São Paulo",
    "estado": "SP"
  }
}
```

### ConsultaInput
```json
{
  "idUsuario": 1,
  "idVoluntario": 2,
  "data": "2023-10-15",
  "horario": "14:00",
  "modalidade": "PRESENCIAL",
  "observacoes": "Primeira consulta"
}
```

### DisponibilidadeInput
```json
{
  "idVoluntario": 1,
  "diaSemana": "SEGUNDA",
  "horaInicio": "09:00",
  "horaFim": "12:00"
}
```

### EnderecoInput
```json
{
  "idUsuario": 1,
  "cep": "12345678",
  "logradouro": "Rua Exemplo",
  "numero": "123",
  "complemento": "Apto 45",
  "bairro": "Centro",
  "cidade": "São Paulo",
  "estado": "SP"
}
```

### TelefoneInput
```json
{
  "idUsuario": 1,
  "ddd": "11",
  "numero": "999999999",
  "tipo": "CELULAR"
}
```

### AvaliacaoInput
```json
{
  "nota": 5,
  "comentario": "Excelente atendimento"
}
```

### FeedbackInput
```json
{
  "conteudo": "Feedback sobre a consulta realizada"
}
```
**Buscar avaliações e feedback do usuário:**
```bash
curl -X GET "http://localhost:8080/consulta/consultas/avaliacoes-feedback?user=assistido" \
  -H "Authorization: Bearer {seu_token_jwt}"
```

Alternativo:
```bash
curl -X GET "http://localhost:8080/consulta/avaliacoes-feedback?user=voluntario" \
  -H "Authorization: Bearer {seu_token_jwt}"
```

**Resposta:**
```json
{
  "feedbacks": [],
  "avaliacoes": []
}
```