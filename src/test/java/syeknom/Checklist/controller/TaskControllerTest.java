package syeknom.Checklist.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import syeknom.Checklist.dto.StatusUpdateDTO;
import syeknom.Checklist.dto.TaskCreateDTO;
import syeknom.Checklist.dto.TaskResponseDTO;
import syeknom.Checklist.model.TaskStatus;
import syeknom.Checklist.service.TaskService;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @InjectMocks
    private TaskController taskController;

    private TaskCreateDTO taskCreateDTO;
    private TaskResponseDTO taskResponseDTO;
    private StatusUpdateDTO statusUpdateDTO;

    @BeforeEach
    void setUp() {
        taskCreateDTO = new TaskCreateDTO();
        taskCreateDTO.setTitle("Reunião");
        taskCreateDTO.setDescription("Descrição");
        taskCreateDTO.setDueDate(LocalDate.now().plusDays(1));
        taskCreateDTO.setPriority(1);
        taskCreateDTO.setCategoryID(1L);

        taskResponseDTO = new TaskResponseDTO();
        taskResponseDTO.setId(1L);
        taskResponseDTO.setTitle("Reunião");
        taskResponseDTO.setStatus("PENDING");
        taskResponseDTO.setCategoryName("Trabalho");

        statusUpdateDTO = new StatusUpdateDTO();
        statusUpdateDTO.setStatus(TaskStatus.IN_PROGRESS);
    }

    @Test
    void create_DeveRetornarCreated() {
        when(taskService.create(any(TaskCreateDTO.class))).thenReturn(taskResponseDTO);

        var response = taskController.create(taskCreateDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().getId());
        verify(taskService, times(1)).create(taskCreateDTO);
    }

    @Test
    void list_DeveRetornarTodasAsTasks() {
        List<TaskResponseDTO> tasks = Arrays.asList(taskResponseDTO);
        when(taskService.listAll()).thenReturn(tasks);

        List<TaskResponseDTO> result = taskController.list();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Reunião", result.get(0).getTitle());
        verify(taskService, times(1)).listAll();
    }

    @Test
    void getById_DeveRetornarTask() {
        when(taskService.getById(1L)).thenReturn(taskResponseDTO);

        TaskResponseDTO result = taskController.getById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(taskService, times(1)).getById(1L);
    }

    @Test
    void update_DeveRetornarTaskAtualizada() {
        when(taskService.update(eq(1L), any(TaskCreateDTO.class))).thenReturn(taskResponseDTO);

        TaskResponseDTO result = taskController.update(1L, taskCreateDTO);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(taskService, times(1)).update(1L, taskCreateDTO);
    }

    @Test
    void patchStatus_DeveAtualizarStatus() {
        when(taskService.patchStatus(1L, TaskStatus.IN_PROGRESS)).thenReturn(taskResponseDTO);

        TaskResponseDTO result = taskController.patchStatus(1L, statusUpdateDTO);

        assertNotNull(result);
        verify(taskService, times(1)).patchStatus(1L, TaskStatus.IN_PROGRESS);
    }

    @Test
    void delete_DeveChamarService() {
        doNothing().when(taskService).delete(1L);

        assertDoesNotThrow(() -> taskController.delete(1L));
        verify(taskService, times(1)).delete(1L);
    }

    @Test
    void findByStatus_DeveRetornarTasksFiltradas() {
        List<TaskResponseDTO> tasks = Arrays.asList(taskResponseDTO);
        when(taskService.findByStatus(TaskStatus.PENDING)).thenReturn(tasks);

        List<TaskResponseDTO> result = taskController.findByStatus(TaskStatus.PENDING);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(taskService, times(1)).findByStatus(TaskStatus.PENDING);
    }
}