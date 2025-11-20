package syeknom.Checklist.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import syeknom.Checklist.dto.CategoryCreateDTO;
import syeknom.Checklist.dto.CategoryResponseDTO;
import syeknom.Checklist.model.Category;
import syeknom.Checklist.service.CategoryService;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryControllerTest {

    @Mock
    private CategoryService categoryService;

    @InjectMocks
    private CategoryController categoryController;

    private Category category;
    private CategoryCreateDTO categoryCreateDTO;

    @BeforeEach
    void setUp() {
        category = new Category("Trabalho", "Tarefas do trabalho");
        category.setId(1L);

        categoryCreateDTO = new CategoryCreateDTO();
        categoryCreateDTO.setName("Trabalho");
        categoryCreateDTO.setDescription("Tarefas do trabalho");
    }

    @Test
    void createCategory_DeveRetornarCreated() {
        when(categoryService.save(any(Category.class))).thenReturn(category);

        ResponseEntity<CategoryResponseDTO> response = categoryController.createCategory(categoryCreateDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        assertEquals("Trabalho", response.getBody().getName());
    }

    @Test
    void getAllCategories_DeveRetornarLista() {
        List<Category> categories = Arrays.asList(category);
        when(categoryService.findAll()).thenReturn(categories);

        ResponseEntity<List<CategoryResponseDTO>> response = categoryController.getAllCategories();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Trabalho", response.getBody().get(0).getName());
    }

    @Test
    void getCategoryById_QuandoExiste_DeveRetornarCategoria() {
        when(categoryService.findById(1L)).thenReturn(Optional.of(category));

        ResponseEntity<CategoryResponseDTO> response = categoryController.getCategoryById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void getCategoryById_QuandoNaoExiste_DeveRetornarNotFound() {
        when(categoryService.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity<CategoryResponseDTO> response = categoryController.getCategoryById(99L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
    
    @Test
    void deveRetornarNoContentAoDeletarCategoriaExistente() {
        when(categoryService.findById(1L)).thenReturn(Optional.of(category));
        doNothing().when(categoryService).deleteById(1L);

        ResponseEntity<Void> response = categoryController.deleteCategory(1L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(categoryService, times(1)).deleteById(1L);
    }
}