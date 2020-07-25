package by.bntu.backend.controller;

import by.bntu.backend.domain.Project;
import by.bntu.backend.domain.Task;
import by.bntu.backend.domain.Views;
import by.bntu.backend.dto.TaskRequestDto;
import by.bntu.backend.service.ProjectService;
import by.bntu.backend.service.TaskService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {

    private final TaskService taskService;
    private final ConversionService conversionService;
    private final ProjectService projectService;


    @Autowired
    public TaskController(TaskService taskService, ConversionService conversionService, ProjectService projectService) {
        this.taskService = taskService;
        this.conversionService = conversionService;
        this.projectService = projectService;
    }

    @GetMapping("/tasks/{taskId}")
    @JsonView(Views.FullTask.class)
    public Task getTask(
            @PathVariable("taskId") Long taskId
    ) {
        return taskService.getTask(taskId);
    }

    @GetMapping("/projects/{projectId}/tasks")
    @JsonView(Views.IdName.class)
    public List<Task> getAllTasksForProject(
            @PathVariable("projectId") Long projectId
    ) {
        return taskService.getAllTasksForProject(projectId);
    }

    @PostMapping("/projects/{projectId}/tasks")
    public Task createTask(
            @PathVariable("projectId") Long projectId,
            @RequestBody TaskRequestDto taskRequestDto
    ) {
        Project project = projectService.getProject(projectId);
        Task task = conversionService.convert(taskRequestDto, Task.class);
        task.setProject(project);
        return taskService.saveTask(task);
    }

    @DeleteMapping("/tasks/{taskId}")
    public void deleteTask(@PathVariable("taskId") Long taskId) {
        taskService.deleteTaskById(taskId);
    }
}
