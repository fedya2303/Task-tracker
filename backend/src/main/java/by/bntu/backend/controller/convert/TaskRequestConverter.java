package by.bntu.backend.controller.convert;

import by.bntu.backend.domain.Status;
import by.bntu.backend.domain.Task;
import by.bntu.backend.dto.TaskRequestDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class TaskRequestConverter<S, T> extends EntityConverter<S, T> {

    protected Task doConvert(TaskRequestDto request, Task task) {
        task.setName(request.getName());
        task.setDescription(request.getDescription());
        task.setStatus(Status.valueOf(request.getStatus()));
        return task;
    }
}
