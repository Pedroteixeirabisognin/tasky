# Tasky — Agent Instructions

Este projeto utiliza Spec-Driven Development.

Antes de implementar qualquer funcionalidade, consulte:

- `specs/001-task-management/spec.md`
- `specs/001-task-management/plan.md`
- `specs/001-task-management/tasks.md`

## Regras

1. `spec.md` é a fonte de verdade para requisitos funcionais.
2. `plan.md` é a fonte de verdade para decisões técnicas.
3. `tasks.md` define a ordem de implementação.
4. Não implemente funcionalidades fora da spec.
5. Não avance para outra task sem solicitação.
6. Não altere decisões arquiteturais do plan sem aprovação.
7. Caso exista ambiguidade ou conflito entre os documentos,
   informe antes de decidir.
8. Crie ou atualize testes correspondentes à task implementada.
9. Execute os testes aplicáveis após a implementação.
10. Não marque uma task como concluída se ela não estiver
    completamente implementada e validada.