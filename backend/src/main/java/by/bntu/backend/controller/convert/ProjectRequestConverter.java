package by.bntu.backend.controller.convert;

import by.bntu.backend.domain.Project;
import by.bntu.backend.domain.Status;
import by.bntu.backend.dto.ProjectRequestDto;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ProjectRequestConverter<S, T> extends EntityConverter<S, T> {

    protected Project doConvert(ProjectRequestDto request, Project project) {
        project.setId(request.getId());
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setStatus(Status.valueOf(request.getStatus()));
        return project;
    }
}
