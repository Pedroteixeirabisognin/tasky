# Tasky — Implementation Tasks

**Versão:** 1.0  
**Baseado em:** `spec.md` e `plan.md`

> Marcar uma tarefa como `[x]` somente depois de implementada e validada.

## Fase 1 — Bootstrap do projeto

- [x] **T001** Criar projeto Spring Boot com Java 25 e Gradle.
- [ ] **T002** Adicionar Spring for GraphQL.
- [ ] **T003** Adicionar Spring Data JPA.
- [ ] **T004** Adicionar driver MySQL.
- [ ] **T005** Configurar conexão com MySQL.
- [ ] **T006** Criar banco de desenvolvimento do Tasky.
- [ ] **T007** Configurar estrutura inicial Package by Feature.

Critério da fase: a aplicação inicia e consegue conectar ao MySQL.

## Fase 2 — Usuário

- [ ] **T008** Criar modelo `Usuario`.
- [ ] **T009** Criar `UsuarioRepository`.
- [ ] **T010** Criar `UsuarioService`.
- [ ] **T011** Criar usuário inicial para desenvolvimento.
- [ ] **T012** Criar testes básicos de persistência de usuário.

Critério da fase: um usuário pode ser persistido e recuperado.

## Fase 3 — Tags

- [ ] **T013** Criar modelo `Tag`.
- [ ] **T014** Relacionar Tag ao usuário.
- [ ] **T015** Criar `TagRepository`.
- [ ] **T016** Criar `TagService`.
- [ ] **T017** Implementar criação de tag.
- [ ] **T018** Impedir tags com mesmo nome para o mesmo usuário.
- [ ] **T019** Criar Query GraphQL `tags`.
- [ ] **T020** Criar Mutation GraphQL `criarTag`.
- [ ] **T021** Criar Mutation GraphQL `excluirTag`.
- [ ] **T022** Criar testes da feature de tags.

## Fase 4 — Tarefa

- [ ] **T023** Criar enum `TipoTarefa`.
- [ ] **T024** Criar modelo `Tarefa`.
- [ ] **T025** Relacionar Tarefa ao usuário.
- [ ] **T026** Implementar relacionamento N:N entre Tarefa e Tag.
- [ ] **T027** Criar `TarefaRepository`.
- [ ] **T028** Criar `TarefaService`.
- [ ] **T029** Criar modelo `ConfiguracaoTarefa`.
- [ ] **T030** Criar representação dos dias da semana.
- [ ] **T031** Relacionar configurações à tarefa.
- [ ] **T032** Implementar vigência das configurações.
- [ ] **T033** Implementar validações de tarefa `UNICA`.
- [ ] **T034** Implementar validações de tarefa `RECORRENTE`.
- [ ] **T035** Criar testes das regras de tarefa.

Critério da fase: tarefas únicas e recorrentes podem ser representadas corretamente.

## Fase 5 — GraphQL de Tarefas

- [ ] **T036** Definir `Tarefa` no schema GraphQL.
- [ ] **T037** Criar `CriarTarefaInput`.
- [ ] **T038** Criar `AtualizarTarefaInput`.
- [ ] **T039** Criar Mutation `criarTarefa`.
- [ ] **T040** Criar Query `tarefa`.
- [ ] **T041** Criar Query `tarefas`.
- [ ] **T042** Criar Mutation `atualizarTarefa`.
- [ ] **T043** Implementar versionamento da configuração ao atualizar.
- [ ] **T044** Criar Mutation `excluirTarefa`.
- [ ] **T045** Criar testes GraphQL de tarefas.

## Fase 6 — Ocorrências

- [ ] **T046** Criar modelo não persistido `Ocorrencia`.
- [ ] **T047** Implementar cálculo de ocorrência para tarefa única.
- [ ] **T048** Implementar cálculo de ocorrência para tarefa recorrente.
- [ ] **T049** Respeitar data inicial da recorrência.
- [ ] **T050** Respeitar data final da recorrência.
- [ ] **T051** Selecionar configuração histórica correta.
- [ ] **T052** Implementar serviço de consulta das tarefas previstas para uma data.
- [ ] **T053** Criar Query GraphQL `tarefasDoDia`.
- [ ] **T054** Criar testes de recorrência.

Casos mínimos:

- SEG/QUA/SEX + consulta na quarta → ocorrência;
- SEG/QUA/SEX + consulta na terça → nenhuma ocorrência;
- consulta antes da data inicial → nenhuma ocorrência;
- consulta depois da data final → nenhuma ocorrência.

## Fase 7 — Realizações

- [ ] **T055** Criar modelo `Realizacao`.
- [ ] **T056** Criar `RealizacaoRepository`.
- [ ] **T057** Criar `RealizacaoService`.
- [ ] **T058** Implementar conclusão de ocorrência.
- [ ] **T059** Impedir duas realizações para a mesma tarefa/data.
- [ ] **T060** Impedir conclusão em uma data sem ocorrência.
- [ ] **T061** Implementar desfazer conclusão.
- [ ] **T062** Criar Mutation `concluirTarefa`.
- [ ] **T063** Criar Mutation `desfazerConclusao`.
- [ ] **T064** Integrar realização com `tarefasDoDia`.
- [ ] **T065** Criar testes de realizações.

## Fase 8 — Conclusão Diária

- [ ] **T066** Criar serviço para avaliar determinado dia.
- [ ] **T067** Buscar todas as ocorrências previstas no dia.
- [ ] **T068** Verificar suas realizações.
- [ ] **T069** Considerar o dia completo quando todas forem concluídas.
- [ ] **T070** Considerar automaticamente completo um dia sem tarefas.
- [ ] **T071** Criar testes das regras R18 e R19.

## Fase 9 — Ciclo de Avanço

- [ ] **T072** Criar `StatusCiclo`.
- [ ] **T073** Criar modelo `Ciclo`.
- [ ] **T074** Criar `CicloRepository`.
- [ ] **T075** Criar `CicloService`.
- [ ] **T076** Implementar ciclo fixo de 14 dias.
- [ ] **T077** Calcular dia atual do ciclo.
- [ ] **T078** Calcular percentual de Avanço.
- [ ] **T079** Calcular dias incompletos.
- [ ] **T080** Calcular tolerâncias restantes.
- [ ] **T081** Determinar se o ciclo ainda pode ser conquistado.
- [ ] **T082** Avaliar ciclo no 14º dia.
- [ ] **T083** Marcar ciclo como `CONQUISTADO` com 0–2 faltas.
- [ ] **T084** Marcar ciclo como `NAO_CONQUISTADO` com 3+ faltas.
- [ ] **T085** Iniciar automaticamente o próximo ciclo.
- [ ] **T086** Preservar ciclos anteriores.
- [ ] **T087** Criar testes das regras de Avanço.

## Fase 10 — GraphQL de Avanço

- [ ] **T088** Definir tipo GraphQL `Ciclo`.
- [ ] **T089** Definir tipo calculado `Avanco`.
- [ ] **T090** Criar Query `avancoAtual`.
- [ ] **T091** Criar Query `ciclos`.
- [ ] **T092** Criar testes GraphQL de Avanço.

## Fase 11 — Erros

- [ ] **T093** Criar modelo padronizado de erros.
- [ ] **T094** Implementar `NOT_FOUND`.
- [ ] **T095** Implementar `VALIDATION_ERROR`.
- [ ] **T096** Implementar `CONFLICT`.
- [ ] **T097** Implementar `INVALID_OPERATION`.
- [ ] **T098** Criar tratamento centralizado de exceções GraphQL.
- [ ] **T099** Impedir exposição de stack traces e detalhes internos.
- [ ] **T100** Criar testes de erros.

## Fase 12 — Integração

- [ ] **T101** Configurar Testcontainers com MySQL.
- [ ] **T102** Criar testes de integração dos repositories.
- [ ] **T103** Criar testes de integração das mutations.
- [ ] **T104** Criar testes de integração das queries.
- [ ] **T105** Testar versionamento das configurações.
- [ ] **T106** Testar fluxo completo de 14 dias.

## Fase 13 — Validação da V1

- [ ] **T107** Revisar implementação contra `spec.md`.
- [ ] **T108** Validar todos os critérios de aceitação.
- [ ] **T109** Executar suíte completa de testes.
- [ ] **T110** Verificar ausência de funcionalidades fora do escopo.
- [ ] **T111** Registrar eventuais divergências entre implementação e spec.
