package syeknom.Checklist.dto;

import org.junit.jupiter.api.Test;
import syeknom.Checklist.model.Category;
import syeknom.Checklist.model.TaskStatus;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class DTOsTest {

    @Test
    void testarCategoryCreateDTO() {
        CategoryCreateDTO dto = new CategoryCreateDTO();
        dto.setName("Trabalho");
        dto.setDescription("Descrição");

        assertEquals("Trabalho", dto.getName());
        assertEquals("Descrição", dto.getDescription());
    }

    @Test
    void testarCategoryResponseDTOO() {
        Category category = new Category("Trabalho", "Descrição");
        category.setId(1L);

        CategoryResponseDTO dto = new CategoryResponseDTO(category);

        assertEquals(1L, dto.getId());
        assertEquals("Trabalho", dto.getName());
        assertEquals("Descrição", dto.getDescription());
    }

    @Test
    void testarTaskCreateDTOO() {
        TaskCreateDTO dto = new TaskCreateDTO();
        dto.setTitle("Título");
        dto.setDescription("Descrição");
        dto.setDueDate(LocalDate.now());
        dto.setPriority(1);
        dto.setCategoryID(1L);

        assertEquals("Título", dto.getTitle());
        assertEquals("Descrição", dto.getDescription());
        assertNotNull(dto.getDueDate());
        assertEquals(1, dto.getPriority());
        assertEquals(1L, dto.getCategoryID());
    }
    
    @Test
    void deveTestarTaskResponseDTOComConstrutorCompleto() {
        LocalDate dueDate = LocalDate.of(2024, 12, 31);
        TaskResponseDTO dto = new TaskResponseDTO(1L, "Tarefa Teste", "Descrição teste", 
            dueDate, "PENDING", 2, "Trabalho");
        
        assertEquals(1L, dto.getId());
        assertEquals("Tarefa Teste", dto.getTitle());
        assertEquals("Descrição teste", dto.getDescription());
        assertEquals(dueDate, dto.getDueDate());
        assertEquals("PENDING", dto.getStatus());
        assertEquals(2, dto.getPriority());
        assertEquals("Trabalho", dto.getCategoryName());
    }

    @Test
    void deveTestarCategoryResponseDTOComConstrutorVazio() {
        CategoryResponseDTO dto = new CategoryResponseDTO();
        dto.setId(1L);
        dto.setName("Categoria Teste");
        dto.setDescription("Descrição teste");
        
        assertEquals(1L, dto.getId());
        assertEquals("Categoria Teste", dto.getName());
        assertEquals("Descrição teste", dto.getDescription());
    }
}