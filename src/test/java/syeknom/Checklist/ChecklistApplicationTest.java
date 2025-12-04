package syeknom.Checklist;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChecklistApplicationTest {

    @Test
    void deveCarregarContextoDaAplicacao() {
        assertDoesNotThrow(() -> {});
    }

    @Test
    void deveExecutarMetodoMainSemErros() {
        assertDoesNotThrow(() -> ChecklistApplication.main(new String[]{}));
    }

    @Test
    void deveRetornarNomeDaAplicacao() {
        ChecklistApplication app = new ChecklistApplication();
        assertNotNull(app);
    }
}