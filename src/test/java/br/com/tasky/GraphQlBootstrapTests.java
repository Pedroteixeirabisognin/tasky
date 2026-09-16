package br.com.tasky;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.execution.GraphQlSource;

import static org.assertj.core.api.Assertions.assertThat;

// Este teste valida GraphQL sem depender da configuração de banco de T004/T005.
@SpringBootTest(properties = {
        "spring.graphql.schema.locations=classpath:graphql-test/",
        "spring.autoconfigure.exclude=org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration"
})
class GraphQlBootstrapTests {

    @Autowired
    private GraphQlSource graphQlSource;

    @Test
    void executesQueryWithAutoConfiguredGraphQl() {
        var result = graphQlSource.graphQl().execute("{ __typename }");

        assertThat(result.getErrors()).isEmpty();
        assertThat(result.<Map<String, Object>>getData())
                .containsEntry("__typename", "Query");
    }
}
