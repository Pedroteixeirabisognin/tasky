# Tasky — Specification

**Versão:** 1.0  
**Status:** Definida

## 1. Visão Geral

O Tasky é uma aplicação para organização de tarefas pessoais e acompanhamento de hábitos.

O usuário poderá cadastrar tarefas únicas ou recorrentes e acompanhar seu progresso através de ciclos de **Avanço** com duração de 14 dias.

O objetivo do Avanço é incentivar a realização das tarefas planejadas, permitindo uma tolerância de até dois dias incompletos por ciclo.

## 2. Tarefas

Uma tarefa representa uma atividade que o usuário pretende realizar.

Toda tarefa deve possuir:

- Nome;
- Tipo;
- Horário;
- Zero ou mais tags.

Existem dois tipos de tarefa:

- `UNICA`;
- `RECORRENTE`.

## 3. Tarefa Única

Uma tarefa única ocorre somente uma vez.

Ela deve possuir:

- Nome;
- Data;
- Horário;
- Zero ou mais tags.

### Regras

**R01** — Toda tarefa única deve possuir uma data.

**R02** — O dia da semana não será informado pelo usuário.

**R03** — O sistema deve determinar automaticamente o dia da semana correspondente à data da tarefa.

## 4. Tarefa Recorrente

Uma tarefa recorrente representa uma atividade que deve acontecer repetidamente em determinados dias da semana.

### Regras

**R04** — Toda tarefa recorrente deve possuir uma data de início.

**R05** — A data de início deve ser escolhida pelo usuário.

**R06** — O usuário deve selecionar um ou mais dias da semana nos quais a tarefa ocorrerá.

**R07** — Nenhuma ocorrência da tarefa poderá existir antes da data de início.

**R08** — A primeira ocorrência será o primeiro dia configurado na recorrência que seja igual ou posterior à data de início.

**R09** — Uma tarefa recorrente poderá não possuir data de término.

**R10** — Opcionalmente, o usuário poderá definir uma data de término.

## 5. Ocorrências

Uma tarefa recorrente não é considerada permanentemente concluída.

Cada realização prevista da tarefa representa uma ocorrência independente.

### Regras

**R11** — Cada ocorrência deve estar associada a uma data.

**R12** — Cada ocorrência deve possuir um estado de conclusão.

**R13** — Concluir uma ocorrência não encerra uma tarefa recorrente.

**R14** — As ocorrências devem respeitar a regra de recorrência da tarefa.

## 6. Tags

Tags permitem categorizar e organizar tarefas.

### Regras

**R15** — Uma tarefa pode possuir zero ou mais tags.

**R16** — Tags possuem finalidade de organização.

**R17** — Uma tag não altera o comportamento ou a recorrência de uma tarefa.

## 7. Conclusão de um Dia

O sistema deve determinar diariamente se o usuário cumpriu todas as tarefas previstas.

**R18** — Um dia é considerado concluído quando todas as tarefas previstas para aquele dia forem concluídas.

**R19** — Caso não exista nenhuma tarefa prevista para determinado dia, esse dia será automaticamente considerado concluído.

## 8. Avanço

Avanço representa o progresso temporal do usuário dentro de um ciclo de 14 dias consecutivos.

**R20** — Cada ciclo de Avanço possui exatamente 14 dias consecutivos.

**R21** — O Avanço é determinado pela posição do dia atual dentro do ciclo.

Cálculo conceitual:

    Avanço = dia atual / 14 * 100

**R22** — Dias incompletos não reduzem o percentual de Avanço.

**R23** — O Avanço continua aumentando conforme o calendário, independentemente do desempenho do usuário.

## 9. Tolerância

**R24** — Cada ciclo permite no máximo dois dias incompletos.

**R25** — Um dia incompleto utiliza uma das tolerâncias disponíveis.

**R26** — Ter até dois dias incompletos não impede que o ciclo seja conquistado.

**R27** — A partir do terceiro dia incompleto, o ciclo não poderá mais ser conquistado.

**R28** — Ultrapassar a tolerância não encerra antecipadamente o ciclo. O ciclo continua normalmente até o 14º dia.

## 10. Encerramento do Ciclo

**R29** — A avaliação do ciclo acontece ao final do 14º dia.

**R30** — Um ciclo com no máximo dois dias incompletos será registrado como conquistado.

**R31** — Um ciclo com três ou mais dias incompletos será considerado não conquistado.

**R32** — O ciclo nunca deve reiniciar antecipadamente devido à quantidade de dias incompletos.

## 11. Novo Ciclo

**R33** — O 15º dia após o início de um ciclo representa o primeiro dia do próximo ciclo.

**R34** — Todo novo ciclo começa com zero dias incompletos.

**R35** — As tolerâncias utilizadas no ciclo anterior não são transferidas para o próximo ciclo.

## 12. Histórico de Ciclos

**R36** — Ciclos conquistados devem permanecer registrados no histórico do usuário.

**R37** — O início de um novo ciclo não deve apagar conquistas anteriores.

## 13. Fora do Escopo da V1

- Autenticação;
- Compartilhamento de tarefas;
- Notificações;
- Integração com Google Calendar;
- Recorrência mensal;
- Recorrência anual;
- Recorrência a cada N semanas;
- Inteligência artificial;
- Ranking entre usuários;
- Sistema de pontos;
- Gamificação além dos ciclos conquistados.

## 14. Critérios Gerais de Aceitação

A V1 será considerada funcionalmente atendida quando for possível:

1. Criar uma tarefa única para uma determinada data;
2. Criar uma tarefa recorrente selecionando dias da semana;
3. Definir a data inicial de uma tarefa recorrente;
4. Associar tags às tarefas;
5. Identificar as tarefas previstas para determinado dia;
6. Registrar a conclusão das ocorrências;
7. Determinar se um dia foi completo ou incompleto;
8. Considerar automaticamente completo um dia sem tarefas;
9. Acompanhar um ciclo de Avanço de 14 dias;
10. Registrar os dias incompletos do ciclo;
11. Aplicar a tolerância de dois dias;
12. Avaliar o resultado ao final do 14º dia;
13. Iniciar automaticamente um novo ciclo;
14. Preservar o histórico de ciclos conquistados.
