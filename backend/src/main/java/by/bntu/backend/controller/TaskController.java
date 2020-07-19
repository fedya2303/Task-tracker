package by.bntu.backend.controller;

import by.bntu.backend.domain.Task;
import by.bntu.backend.domain.Views;
import by.bntu.backend.service.TaskService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TaskController {

    private TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/projects/{projectId}/tasks/{taskId}")
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
}
