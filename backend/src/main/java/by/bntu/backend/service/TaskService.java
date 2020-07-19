package by.bntu.backend.service;

import by.bntu.backend.domain.Task;
import by.bntu.backend.exception.NotFoundResourceException;
import by.bntu.backend.repository.TaskRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private TaskRepo taskRepo;

    @Autowired
    public TaskService(TaskRepo taskRepo) {
        this.taskRepo = taskRepo;
    }


    public Task getTask(Long taskId) {
        Optional<Task> task = taskRepo.findById(taskId);
        return task.orElseThrow(NotFoundResourceException::new);
    }

    public List<Task> getAllTasksForProject(Long projectId) {
        return taskRepo.findAllByProjectId(projectId);
    }
}
