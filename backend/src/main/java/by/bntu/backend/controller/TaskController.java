package by.bntu.backend.controller;

import by.bntu.backend.domain.Project;
import by.bntu.backend.domain.Task;
import by.bntu.backend.domain.User;
import by.bntu.backend.domain.Views;
import by.bntu.backend.dto.TaskRequestDto;
import by.bntu.backend.service.ProjectService;
import by.bntu.backend.service.TaskService;
import by.bntu.backend.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TaskController {

    private final TaskService taskService;
    private final ConversionService conversionService;
    private final ProjectService projectService;
    private UserDetailsServiceImpl userDetailsService;


    @Autowired
    public TaskController(TaskService taskService, ConversionService conversionService, ProjectService projectService, UserDetailsServiceImpl userDetailsService) {
        this.taskService = taskService;
        this.conversionService = conversionService;
        this.projectService = projectService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/projects/{projectId}/tasks/{taskId}")
    @JsonView(Views.FullTask.class)
    public Task getTask(
            @PathVariable("taskId") Long taskId,
            @PathVariable("projectId") Long projectId
    ) {
        Task task = taskService.getTask(taskId);
        Project project = projectService.getProject(projectId);
        boolean contains = project.getTasks().contains(task);
        if (contains) {
            return task;
        }
        return null;
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

    @PutMapping("/projects/{projectId}/tasks/{taskId}")
    public Task updateTask(
            @PathVariable("taskId") Long taskId,
            @PathVariable("projectId") Long projectId,
            @RequestBody TaskRequestDto taskRequestDto,
            Principal principal
    ) {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());

        List<Project> userProjects = user.getProjects().stream().filter(project ->
                project.getId().equals(projectId)
        ).collect(Collectors.toList());

        if (!userProjects.isEmpty()) {
            Project project = projectService.getProject(projectId);
            boolean isProjectTask = project.getTasks().stream().anyMatch(task -> task.getId().equals(taskId));
            if (isProjectTask) {
                Task updatedTask = conversionService.convert(taskRequestDto, Task.class);
                updatedTask.setProject(project);
                return taskService.saveTask(updatedTask);
            }
        }
        return null;
    }

    @DeleteMapping("/tasks/{taskId}")
    public void deleteTask(@PathVariable("taskId") Long taskId) {
        taskService.deleteTaskById(taskId);
    }
}
