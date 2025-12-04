package syeknom.Checklist.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import syeknom.Checklist.dto.TaskCreateDTO;
import syeknom.Checklist.dto.TaskResponseDTO;
import syeknom.Checklist.model.Category;
import syeknom.Checklist.model.Task;
import syeknom.Checklist.model.TaskStatus;
import syeknom.Checklist.repository.CategoryRepository;
import syeknom.Checklist.repository.TaskRepository;

import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Task task;
    private Category category;
    private TaskCreateDTO taskCreateDTO;

    @BeforeEach
    void setUp() {
        category = new Category("Trabalho", "Tarefas do trabalho");
        category.setId(1L);

        task = new Task();
        task.setId(1L);
        task.setTitle("Reunião importante");
        task.setDescription("Preparar apresentação");
        task.setDueDate(LocalDate.now().plusDays(1));
        task.setPriority(1);
        task.setStatus(TaskStatus.PENDING);
        task.setCategory(category);

        taskCreateDTO = new TaskCreateDTO();
        taskCreateDTO.setTitle("Reunião importante");
        taskCreateDTO.setDescription("Preparar apresentação");
        taskCreateDTO.setDueDate(LocalDate.now().plusDays(1));
        taskCreateDTO.setPriority(1);
        taskCreateDTO.setCategoryID(1L);
    }

    @Test
    void create_DeveRetornarTaskResponseDTO() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponseDTO result = taskService.create(taskCreateDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Reunião importante", result.getTitle());
        assertEquals("Trabalho", result.getCategoryName());
    }

    @Test
    void create_QuandoCategoriaNaoEncontrada_DeveLancarExcecao() {
        when(categoryRepository.findById(99L)).thenReturn(Optional.empty());
        taskCreateDTO.setCategoryID(99L);

        assertThrows(EntityNotFoundException.class, () -> {
            taskService.create(taskCreateDTO);
        });
    }

    @Test
    void listAll_DeveRetornarTodasAsTasks() {
        List<Task> tasks = Arrays.asList(task);
        when(taskRepository.findAll()).thenReturn(tasks);

        List<TaskResponseDTO> result = taskService.listAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Reunião importante", result.get(0).getTitle());
    }

    @Test
    void getById_QuandoTaskExiste_DeveRetornarTaskResponseDTO() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        TaskResponseDTO result = taskService.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Reunião importante", result.getTitle());
    }

    @Test
    void getById_QuandoTaskNaoExiste_DeveLancarExcecao() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            taskService.getById(99L);
        });
    }
    
    
    @Test
    void update_DeveRetornarTaskAtualizada() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponseDTO result = taskService.update(1L, taskCreateDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void update_QuandoTaskNaoEncontrada_DeveLancarExcecao() {
        when(taskRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            taskService.update(99L, taskCreateDTO);
        });
    }

    @Test
    void patchStatus_DeveAtualizarStatus() {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskResponseDTO result = taskService.patchStatus(1L, TaskStatus.IN_PROGRESS);

        assertNotNull(result);
        assertEquals(TaskStatus.IN_PROGRESS.name(), result.getStatus());
    }

    @Test
    void delete_QuandoTaskExiste_DeveDeletar() {
        when(taskRepository.existsById(1L)).thenReturn(true);
        doNothing().when(taskRepository).deleteById(1L);

        taskService.delete(1L);

        verify(taskRepository, times(1)).existsById(1L);
        verify(taskRepository, times(1)).deleteById(1L);
    }

    @Test
    void delete_QuandoTaskNaoExiste_DeveLancarExcecao() {
        when(taskRepository.existsById(99L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> {
            taskService.delete(99L);
        });
    }

    @Test
    void findByStatus_DeveRetornarTasksFiltradas() {
        List<Task> tasks = Arrays.asList(task);
        when(taskRepository.findByStatus(TaskStatus.PENDING)).thenReturn(tasks);

        List<TaskResponseDTO> result = taskService.findByStatus(TaskStatus.PENDING);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(TaskStatus.PENDING.name(), result.get(0).getStatus());
    }
}