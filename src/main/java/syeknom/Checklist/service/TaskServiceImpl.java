package syeknom.Checklist.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import syeknom.Checklist.dto.TaskCreateDTO;
import syeknom.Checklist.dto.TaskResponseDTO;
import syeknom.Checklist.model.Category;
import syeknom.Checklist.model.Task;
import syeknom.Checklist.model.TaskStatus;
import syeknom.Checklist.repository.CategoryRepository;
import syeknom.Checklist.repository.TaskRepository;
import syeknom.Checklist.service.TaskService;


import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final CategoryRepository categoryRepository;

    public TaskServiceImpl(TaskRepository taskRepository, CategoryRepository categoryRepository) {
        this.taskRepository = taskRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public TaskResponseDTO create(TaskCreateDTO dto) {
        Category category = categoryRepository.findById(dto.getCategoryID())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        Task t = new Task();
        t.setTitle(dto.getTitle());
        t.setDescription(dto.getDescription());
        t.setDueDate(dto.getDueDate());
        t.setPriority(dto.getPriority());
        t.setCategory(category);
        t.setStatus(TaskStatus.PENDING);

        Task saved = taskRepository.save(t);

        if (dto.getRelatedTaskIds() != null && !dto.getRelatedTaskIds().isEmpty()) {
            dto.getRelatedTaskIds().forEach(relatedId -> {
                Task relatedTask = taskRepository.findById(relatedId)
                        .orElseThrow(() -> new EntityNotFoundException("Tarefa relacionada com ID " + relatedId + " não encontrada"));
                saved.getRelatedTasks().add(relatedTask);
                relatedTask.getRelatedTasks().add(saved);
            });
        }
        // No need to save again, transaction will handle it
        return new TaskResponseDTO(saved);
    }

    @Override
    public List<TaskResponseDTO> listAll() {
        return taskRepository.findAll().stream().map(TaskResponseDTO::new).collect(Collectors.toList());
    }

    @Override
    public TaskResponseDTO getById(Long id) {
        return taskRepository.findById(id).map(TaskResponseDTO::new)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));
    }

    @Override
    public TaskResponseDTO update(Long id, TaskCreateDTO dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));
        Category category = categoryRepository.findById(dto.getCategoryID())
                .orElseThrow(() -> new EntityNotFoundException("Categoria não encontrada"));

        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setDueDate(dto.getDueDate());
        task.setPriority(dto.getPriority());
        task.setCategory(category);

        return new TaskResponseDTO(taskRepository.save(task));
    }

    @Override
    public TaskResponseDTO patchStatus(Long id, TaskStatus status) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa não encontrada"));
        task.setStatus(status);
        return new TaskResponseDTO(taskRepository.save(task));
    }

    @Override
    public void delete(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Tarefa não encontrada");
        }
        taskRepository.deleteById(id);
    }

    @Override
    public List<TaskResponseDTO> findByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status).stream().map(TaskResponseDTO::new).collect(Collectors.toList());
    }


    public TaskResponseDTO linkTask(Long taskId, Long relatedTaskId) {
        if (taskId.equals(relatedTaskId)) {
            throw new IllegalArgumentException("Uma tarefa não pode ser associada a ela mesma.");
        }
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa com ID " + taskId + " não encontrada"));
        Task relatedTask = taskRepository.findById(relatedTaskId)
                .orElseThrow(() -> new EntityNotFoundException("Tarefa relacionada com ID " + relatedTaskId + " não encontrada"));

        task.getRelatedTasks().add(relatedTask);
        relatedTask.getRelatedTasks().add(task);

        return new TaskResponseDTO(task);
    }
}
