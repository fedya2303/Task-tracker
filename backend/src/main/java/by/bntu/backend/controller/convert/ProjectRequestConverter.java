package by.bntu.backend.controller.convert;

import by.bntu.backend.domain.Project;
import by.bntu.backend.domain.Status;
import by.bntu.backend.dto.ProjectCreateRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class ProjectRequestConverter<S, T> extends EntityConverter<S, T> {

    protected Project doConvert(ProjectCreateRequest request, Project project) {
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setStatus(Status.valueOf(request.getStatus()));
        return project;
    }
}
