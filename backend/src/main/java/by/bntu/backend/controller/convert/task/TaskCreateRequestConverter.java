package by.bntu.backend.controller.convert.task;

import by.bntu.backend.domain.Task;
import by.bntu.backend.dto.TaskRequestDto;
import org.springframework.stereotype.Component;

@Component
public class TaskCreateRequestConverter extends TaskRequestConverter<TaskRequestDto, Task> {

    @Override
    public Task convert(TaskRequestDto request) {
        Task task = new Task();
        return doConvert(request, task);
    }
}
