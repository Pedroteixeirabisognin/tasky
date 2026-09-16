# Tasky — Technical Plan

**Versão:** 1.0  
**Status:** Fechado para implementação inicial  
**Baseado em:** `spec.md`

## 1. Contexto

Este documento descreve a estratégia técnica para implementar os requisitos definidos em `spec.md`.

## 2. Stack Tecnológica

- Linguagem: Java
- Versão: Java 25
- Framework: Spring Boot
- Build: Gradle
- Banco de dados: MySQL
- API: GraphQL

### 2.1 Decisão de API

A aplicação utilizará GraphQL como interface principal.

#### Motivação

- Permitir que clientes consultem somente os campos necessários;
- Facilitar consultas envolvendo tarefas, ocorrências, tags e avanço;
- Permitir a composição de diferentes informações em uma operação;
- Utilizar o projeto para aprofundar conhecimentos em GraphQL.

#### Trade-offs

GraphQL adiciona complexidade em relação a uma API REST simples, principalmente em relação ao schema, resolvers e controle das consultas.

Para os requisitos atuais da V1, REST também seria suficiente. A escolha por GraphQL é uma decisão arquitetural e de aprendizado, e não uma necessidade funcional.

## 3. Modelo de Domínio

Conceitos identificados:

- Usuário;
- Tarefa;
- Ciclo;
- Tag;
- Configuração da tarefa;
- Ocorrência;
- Realização;
- Avanço/Progresso.

Usuário, Tarefa e Ciclo possuem identidade própria.

O Avanço será calculado a partir do ciclo e não será persistido como entidade própria.

### 3.1 Estratégia de Ocorrências

As ocorrências de tarefas recorrentes não serão previamente persistidas.

O sistema calculará as ocorrências a partir da regra de recorrência da tarefa.

Somente a realização de uma ocorrência será persistida.

Conceitualmente:

- Regra: o que deveria acontecer;
- Ocorrência: o que deveria acontecer em determinada data;
- Realização: o que efetivamente foi concluído.

#### Motivação

- Evitar geração antecipada de ocorrências;
- Permitir tarefas recorrentes sem data final;
- Reduzir armazenamento de dados derivados;
- Manter a regra de recorrência como fonte das ocorrências.

#### Trade-off

A aplicação precisará calcular quais tarefas estão previstas para uma determinada data durante as consultas.

### 3.2 Histórico das Regras de Recorrência

Alterações realizadas em uma tarefa recorrente não devem modificar retroativamente seu histórico.

Cada alteração na regra de recorrência deverá preservar a configuração anterior e permitir identificar qual configuração estava vigente em determinado período.

### 3.3 Vigência de Alterações

Alterações realizadas na configuração de uma tarefa entram em vigor imediatamente, inclusive para o dia atual.

Datas anteriores ao dia da alteração devem continuar utilizando a configuração histórica correspondente.

## 4. Organização e Arquitetura

A aplicação será um monólito organizado por **Package by Feature**, utilizando arquitetura em camadas.

Estrutura inicial:

    br.com.tasky
    ├── task
    ├── completion
    ├── progress
    └── user

Cada feature poderá conter suas próprias camadas:

    feature/
    ├── controller
    ├── service
    ├── repository
    └── model

Fluxo principal:

    GraphQL
       ↓
    Controller
       ↓
    Service
       ↓
    Repository
       ↓
    MySQL

### Motivação

- Manter código relacionado próximo;
- Facilitar a localização das funcionalidades;
- Aumentar a coesão;
- Facilitar crescimento futuro do projeto;
- Manter menor complexidade estrutural que uma arquitetura hexagonal.

## 5. Modelo de Dados Conceitual

Relacionamentos principais:

- Usuário 1:N Tarefa;
- Usuário 1:N Ciclo;
- Tarefa N:N Tag;
- Tarefa 1:N Configuração da Tarefa;
- Tarefa 1:N Realização.

`Ocorrencia` e `Avanco` serão conceitos calculados, não tabelas próprias.

## 6. Modelo Físico — MySQL

### 6.1 usuario

Campos planejados:

- `id BIGINT` PK;
- `nome VARCHAR(100)`;
- `email VARCHAR(255)` UNIQUE;
- `criado_em TIMESTAMP`.

### 6.2 tarefa

Campos planejados:

- `id BIGINT` PK;
- `usuario_id BIGINT` FK;
- `nome VARCHAR(150)`;
- `ativa BOOLEAN`;
- `criado_em TIMESTAMP`.

### 6.3 configuracao_tarefa

Campos planejados:

- `id BIGINT` PK;
- `tarefa_id BIGINT` FK;
- `tipo VARCHAR(20)`;
- `horario TIME`;
- `data_inicio DATE`;
- `data_fim DATE` opcional;
- `vigencia_inicio TIMESTAMP`;
- `vigencia_fim TIMESTAMP` opcional.

A configuração preservará o histórico de alterações da tarefa.

### 6.4 configuracao_dia_semana

Relacionará uma configuração aos seus dias de recorrência.

Representação ISO:

- 1 = Segunda;
- 2 = Terça;
- 3 = Quarta;
- 4 = Quinta;
- 5 = Sexta;
- 6 = Sábado;
- 7 = Domingo.

### 6.5 tag

Campos planejados:

- `id BIGINT` PK;
- `usuario_id BIGINT` FK;
- `nome VARCHAR(50)`.

O par `(usuario_id, nome)` deverá ser único.

### 6.6 tarefa_tag

Tabela associativa N:N entre tarefa e tag.

### 6.7 realizacao

Campos planejados:

- `id BIGINT` PK;
- `tarefa_id BIGINT` FK;
- `data_ocorrencia DATE`;
- `concluida_em TIMESTAMP`.

O par `(tarefa_id, data_ocorrencia)` deverá ser único.

### 6.8 ciclo

Campos planejados:

- `id BIGINT` PK;
- `usuario_id BIGINT` FK;
- `data_inicio DATE`;
- `data_fim DATE`;
- `dias_incompletos INT`;
- `status VARCHAR(20)`.

Status:

- `EM_ANDAMENTO`;
- `CONQUISTADO`;
- `NAO_CONQUISTADO`.

### 6.9 Questão de consistência

Alterações feitas no dia atual entram em vigor imediatamente. A implementação deverá manter consistência quando já existir uma realização para uma ocorrência que deixe de existir após alteração no mesmo dia. Esse caso deverá ser tratado explicitamente durante a implementação e testes, sem reescrever o histórico de dias anteriores.

## 7. Contrato GraphQL

### 7.1 Scalars

    scalar Date
    scalar Time
    scalar DateTime

Mapeamento pretendido:

- `Date` → `LocalDate`;
- `Time` → `LocalTime`;
- timestamps → estratégia baseada em UTC.

### 7.2 Enums

    enum TipoTarefa {
        UNICA
        RECORRENTE
    }

    enum DiaSemana {
        SEGUNDA
        TERCA
        QUARTA
        QUINTA
        SEXTA
        SABADO
        DOMINGO
    }

    enum StatusCiclo {
        EM_ANDAMENTO
        CONQUISTADO
        NAO_CONQUISTADO
    }

### 7.3 Queries

    type Query {
        tarefa(id: ID!): Tarefa
        tarefas: [Tarefa!]!
        tarefasDoDia(data: Date!): [Ocorrencia!]!
        tags: [Tag!]!
        avancoAtual: Avanco!
        ciclos: [Ciclo!]!
    }

### 7.4 Mutations

    type Mutation {
        criarTarefa(input: CriarTarefaInput!): Tarefa!
        atualizarTarefa(id: ID!, input: AtualizarTarefaInput!): Tarefa!
        excluirTarefa(id: ID!): Boolean!

        concluirTarefa(tarefaId: ID!, data: Date!): Ocorrencia!
        desfazerConclusao(tarefaId: ID!, data: Date!): Ocorrencia!

        criarTag(nome: String!): Tag!
        excluirTag(id: ID!): Boolean!
    }

### 7.5 Ocorrência GraphQL

`Ocorrencia` será um tipo calculado e não corresponderá diretamente a uma entidade JPA.

Campos planejados:

- tarefa;
- data;
- horário;
- concluída;
- concluídaEm.

### 7.6 Avanço GraphQL

Campos planejados:

- ciclo;
- diaAtual;
- percentual;
- diasIncompletos;
- toleranciasRestantes;
- aindaPodeSerConquistado.

### 7.7 Inputs

`CriarTarefaInput` deverá suportar tarefas únicas e recorrentes.

Validações:

Tarefa `UNICA`:

- data obrigatória;
- dias da semana não aplicáveis.

Tarefa `RECORRENTE`:

- data de início obrigatória;
- pelo menos um dia da semana obrigatório;
- data final opcional.

## 8. Datas e Horários

A aplicação utilizará `java.time`.

- Datas de negócio: `LocalDate`;
- Horários: `LocalTime`;
- Timestamps: armazenados de forma consistente em UTC;
- Dia da semana: derivado de `LocalDate` usando o padrão ISO 1–7.

## 9. Responsabilidades das Camadas

### Controller

- Receber Queries e Mutations GraphQL;
- Fazer validação estrutural básica;
- Encaminhar operações ao Service;
- Retornar o contrato GraphQL.

Controllers não devem implementar regras de negócio.

### Service

- Orquestrar casos de uso;
- Consultar repositories;
- Aplicar regras envolvendo múltiplos conceitos;
- Controlar transações.

### Model

Entidades e objetos do domínio podem conter comportamentos diretamente relacionados ao próprio conceito.

Exemplos conceituais:

    ciclo.calcularAvanco()
    ciclo.podeSerConquistado()

### Repository

Responsável pelo acesso à persistência.

Repositories não devem conter regras de negócio.

### 9.1 Cálculo de Ocorrências

Para determinar se uma tarefa possui ocorrência em uma data:

1. Identificar a configuração aplicável;
2. Verificar data inicial;
3. Verificar data final, caso exista;
4. Determinar o dia da semana;
5. Verificar a regra de recorrência;
6. Consultar se existe realização registrada;
7. Montar a ocorrência calculada.

## 10. Tratamento de Erros

A aplicação possuirá tratamento centralizado de erros GraphQL.

Categorias iniciais:

- `NOT_FOUND`;
- `VALIDATION_ERROR`;
- `CONFLICT`;
- `INVALID_OPERATION`.

Erros internos não deverão expor stack traces ou detalhes técnicos ao cliente.

Os códigos de erro deverão ser disponibilizados por meio de `extensions` do GraphQL.

## 11. Estratégia de Testes

### Testes unitários

Prioridade para:

- Recorrência;
- Datas;
- Dia da semana;
- Avanço;
- Tolerância;
- Resultado do ciclo;
- Conclusão diária;
- Dias sem tarefas.

### Testes de integração

Prioridade para:

- JPA;
- Relacionamentos;
- Versionamento de configurações;
- Queries GraphQL;
- Mutations GraphQL;
- Transações.

Quando o comportamento real do banco for relevante, os testes deverão utilizar MySQL via Testcontainers em vez de substituir o banco por H2.

## 12. Transações

Operações que modificam múltiplos registros relacionados deverão ser transacionais.

Exemplo: atualização de uma tarefa recorrente deverá encerrar a configuração vigente, criar uma nova configuração e registrar seus dias de recorrência na mesma transação.

## 13. Observabilidade

A aplicação utilizará logging por meio da abstração fornecida pelo Spring.

Devem ser registrados eventos relevantes para diagnóstico e erros inesperados.

`System.out.println` não deverá ser utilizado como mecanismo de logging da aplicação.

## 14. Resumo das Decisões

- Java 25;
- Spring Boot;
- Gradle;
- MySQL;
- GraphQL;
- Monólito;
- Package by Feature;
- Arquitetura em camadas;
- Ocorrências calculadas;
- Realizações persistidas;
- Histórico de configurações preservado;
- Alterações válidas imediatamente no dia atual;
- Avanço calculado;
- Testes unitários + integração com Testcontainers/MySQL.
