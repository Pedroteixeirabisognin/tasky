package br.com.tasky;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// O driver e a conexão serão adicionados em T004/T005.
@SpringBootTest(useMainMethod = SpringBootTest.UseMainMethod.ALWAYS,
        properties = "spring.autoconfigure.exclude=org.springframework.boot.jdbc.autoconfigure.DataSourceAutoConfiguration")
class TaskyApplicationTests {

    @Test
    void contextLoads() {
    }
}
