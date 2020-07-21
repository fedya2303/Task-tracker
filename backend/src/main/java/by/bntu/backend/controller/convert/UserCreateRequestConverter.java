package by.bntu.backend.controller.convert;

import by.bntu.backend.domain.Project;
import by.bntu.backend.dto.ProjectCreateRequest;
import org.springframework.stereotype.Component;

@Component
public class UserCreateRequestConverter extends ProjectRequestConverter<ProjectCreateRequest, Project> {

    @Override
    public Project convert(ProjectCreateRequest request) {
        Project project = new Project();
        return doConvert(request, project);
    }
}
