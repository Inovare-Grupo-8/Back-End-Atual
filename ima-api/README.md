Resumo
- Backend REST para o projeto IMA: gerencia consultas, disponibilidade, voluntários, doações e usuários.
- Implementado em Java com Spring Boot e Maven.

Índice
- Visão geral
- Tecnologias
- Estrutura do projeto
- Como executar
- Variáveis de ambiente necessárias
- Documentação e testes

Visão geral
- API responsável pelas operações principais do sistema (consulta, disponibilidade, especialidades, cadastro de usuários, avaliações, etc.).
- Padrão de camadas: controllers, use cases, domain, gateways e infra.

Tecnologias
- Java 17+ (compatível com o projeto)
- Spring Boot
- Maven
- Banco de dados: MySQL (configurável via variáveis de ambiente)
- Arquivos auxiliares em `docs/` e testes em `test/`

Estrutura do projeto (principais arquivos / pastas)
- `src/main/java` — código-fonte da aplicação
- `src/main/resources` — configurações e recursos
- `docs/` — documentação (ex.: `ENDPOINTS.md`)
- `test/` — testes unitários
- `uploads/` — arquivos enviados de exemplo
- `compose.yaml` — orquestração (se aplicável)
- `pom.xml` — build e dependências

Como executar (Windows)
- Build: `mvnw.cmd clean package`
- Executar: `mvnw.cmd spring-boot:run`
- Alternativamente, executar o JAR gerado em `target/` com `java -jar target/<artifact>.jar`

Variáveis de ambiente necessárias
- `EMAIL`
- `FILA_GRATUIDADE`
- `FILA_AGENDAMENTO_GRATUIDADE`
- `MEU_BD` (URL JDBC do banco)
- `PASS_BD`
- `SENHA`
- `USER_BD`

Observações sobre variáveis
- Defina estas variáveis no ambiente do sistema ou em `src/main/resources/application.properties` sem inserir valores públicos no repositório.
- As variáveis relacionadas a filas representam nomes/configurações de filas usadas pelo sistema.

Documentação e testes
- Endpoints descritos em `docs/ENDPOINTS.md`
- Testes de controlador em `test/java/...` (execute com `mvnw.cmd test`)

Licença / Contato
- Informação de licença e contato devem ser adicionadas conforme necessário.gas;PASS_BD=ima_pass;SENHA=hrmb brmz lwdj agcr;USER_BD=ima;FILA_AGENDAMENTO_GRATUIDADE=fila_agendamento