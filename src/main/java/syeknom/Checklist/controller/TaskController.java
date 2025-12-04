package syeknom.Checklist.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import syeknom.Checklist.dto.StatusUpdateDTO;
import syeknom.Checklist.dto.TaskCreateDTO;
import syeknom.Checklist.dto.TaskResponseDTO;
import syeknom.Checklist.model.TaskStatus;
import syeknom.Checklist.service.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Validated
@Tag(name = "Controlador de Tarefas", description = "Endpoints para gerenciamento de tarefas")
public class TaskController {

    private final TaskService service;

    public TaskController(TaskService service) {
        this.service = service;
    }

    // -------------------------------------------------------------------------
    @Operation(summary = "Cria uma nova tarefa",
            description = "Cria uma tarefa com título, descrição, data de vencimento, prioridade e categoria associada.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Tarefa criada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos"),
            @ApiResponse(responseCode = "500", description = "Erro interno no servidor")
    })
    @PostMapping
    public ResponseEntity<TaskResponseDTO> create(@Valid @RequestBody TaskCreateDTO dto) {
        TaskResponseDTO created = service.create(dto);
        URI location = URI.create("/api/tasks/" + created.getId());
        return ResponseEntity.created(location).body(created);
    }

    // -------------------------------------------------------------------------
    @Operation(summary = "Lista todas as tarefas",
            description = "Retorna uma lista de todas as tarefas cadastradas no sistema.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    })
    @GetMapping
    public List<TaskResponseDTO> list() {
        return service.listAll();
    }

    // -------------------------------------------------------------------------
    @Operation(summary = "Busca uma tarefa pelo ID",
            description = "Retorna os dados completos de uma tarefa específica com base no ID informado.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa encontrada"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    @GetMapping("/{id}")
    public TaskResponseDTO getById(@PathVariable Long id) {
        return service.getById(id);
    }

    // -------------------------------------------------------------------------
    @Operation(summary = "Atualiza uma tarefa existente",
            description = "Atualiza título, descrição, data, prioridade ou categoria de uma tarefa específica.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Tarefa atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PutMapping("/{id}")
    public TaskResponseDTO update(@PathVariable Long id, @Valid @RequestBody TaskCreateDTO dto) {
        return service.update(id, dto);
    }

    // -------------------------------------------------------------------------
    @Operation(summary = "Atualiza o status de uma tarefa",
            description = "Altera apenas o status da tarefa (exemplo: PENDING, COMPLETED, CANCELLED).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada"),
            @ApiResponse(responseCode = "400", description = "Status inválido")
    })
    @PatchMapping("/{id}/status")
    public TaskResponseDTO patchStatus(@PathVariable Long id, @Valid @RequestBody StatusUpdateDTO dto) {
        return service.patchStatus(id, dto.getStatus());
    }

    // -------------------------------------------------------------------------
    @Operation(summary = "Exclui uma tarefa pelo ID",
            description = "Remove permanentemente uma tarefa existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Tarefa excluída com sucesso"),
            @ApiResponse(responseCode = "404", description = "Tarefa não encontrada")
    })
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // -------------------------------------------------------------------------
    @Operation(summary = "Busca tarefas por status",
            description = "Retorna todas as tarefas que possuem o status informado (exemplo: PENDING, COMPLETED).")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nenhuma tarefa encontrada com o status informado")
    })
    @GetMapping("/status/{status}")
    public List<TaskResponseDTO> findByStatus(@PathVariable TaskStatus status) {
        return service.findByStatus(status);
    }
}
