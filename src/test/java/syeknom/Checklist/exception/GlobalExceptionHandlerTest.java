package syeknom.Checklist.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.dao.DataIntegrityViolationException;
import jakarta.persistence.EntityNotFoundException;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler exceptionHandler = new GlobalExceptionHandler();

    @Test
    void handleEntityNotFoundException_ShouldReturnNotFound() {
        EntityNotFoundException exception = new EntityNotFoundException("Categoria não encontrada");
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleNotFound(exception);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(404, response.getStatusCode().value());
        assertTrue(response.getBody().containsKey("error"));
        assertEquals("Categoria não encontrada", response.getBody().get("error"));
    }

    @Test
    void handleDataIntegrityViolationException_ShouldReturnConflict() {
        DataIntegrityViolationException exception = new DataIntegrityViolationException("Erro de chave duplicada");
        
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleConflict(exception);
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(409, response.getStatusCode().value());
        assertTrue(response.getBody().containsKey("error"));
        assertTrue(response.getBody().get("error").contains("Violação de integridade"));
    }

    @Test
    void handleGenericException_ShouldReturnInternalError() {
        Exception exception = new Exception("Erro inesperado");
        
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleGeneric(exception);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(500, response.getStatusCode().value());
        assertTrue(response.getBody().containsKey("error"));
        assertTrue(response.getBody().get("error").contains("Erro interno"));
    }
    
    @Test
    void deveRetornarBadRequestParaValidacaoDeCampos() {
        BindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "taskCreateDTO");
        bindingResult.addError(new FieldError("taskCreateDTO", "title", "Título é obrigatório"));
        bindingResult.addError(new FieldError("taskCreateDTO", "priority", "Prioridade deve ser entre 1 e 5"));
        
        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(null, bindingResult);
        
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleValidation(exception);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Título é obrigatório", response.getBody().get("title"));
        assertEquals("Prioridade deve ser entre 1 e 5", response.getBody().get("priority"));
    }

    @Test
    void deveRetornarInternalErrorParaRuntimeException() {
        RuntimeException exception = new RuntimeException("Erro de runtime inesperado");
        
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleGeneric(exception);
        
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().containsKey("error"));
        assertTrue(response.getBody().get("error").contains("Erro interno"));
    }

    @Test
    void deveRetornarConflictParaDataIntegrityViolationExceptionComCausaEspecifica() {
        DataIntegrityViolationException exception = new DataIntegrityViolationException("Erro de constraint", 
            new RuntimeException("Duplicate entry 'categoria'"));
        
        ResponseEntity<Map<String, String>> response = exceptionHandler.handleConflict(exception);
        
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertTrue(response.getBody().containsKey("error"));
        assertTrue(response.getBody().get("error").contains("Duplicate entry"));
    }
}