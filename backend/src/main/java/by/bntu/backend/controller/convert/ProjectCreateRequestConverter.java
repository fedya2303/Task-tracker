
package by.bntu.backend.controller.convert;

import by.bntu.backend.domain.Project;
import by.bntu.backend.dto.ProjectRequestDto;
import org.springframework.stereotype.Component;

@Component
public class ProjectCreateRequestConverter extends ProjectRequestConverter<ProjectRequestDto, Project> {

    @Override
    public Project convert(ProjectRequestDto request) {
        Project project = new Project();
        return doConvert(request, project);
    }
}