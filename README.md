# Tasky

Projeto base com Spring Boot 4.1.1, Java 25 e Gradle 9.7.1.

Requisito: JDK 25 instalado e disponível no PATH ou em JAVA_HOME.
O Wrapper e as dependências exigem internet no primeiro uso, se não estiverem em cache.

No Windows (PowerShell):

```powershell
.\gradlew.bat clean build
.\gradlew.bat bootRun
```

O build executa os testes e gera o JAR executável:

```shell
java -jar build/libs/tasky-0.0.1-SNAPSHOT.jar
```

Com a T003, Spring Data JPA e Hibernate estão disponíveis. A execução por
`bootRun` ou pelo JAR depende do driver MySQL (T004) e da configuração de conexão
(T005), ainda pendentes. Até lá, a inicialização normal falha por falta de DataSource.

Os testes de bootstrap desabilitam a autoconfiguração de DataSource apenas em seus
próprios contextos. O teste de dependências verifica Spring Data JPA e a descoberta
do provedor Hibernate, sem testar persistência nem conectar a um banco.
