package by.bntu.backend.controller;

import by.bntu.backend.domain.Project;
import by.bntu.backend.domain.User;
import by.bntu.backend.domain.Views;
import by.bntu.backend.dto.ProjectCreateRequest;
import by.bntu.backend.service.ProjectService;
import by.bntu.backend.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Set;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final UserDetailsServiceImpl userDetailsService;
    private final ConversionService conversionService;

    @Autowired
    public ProjectController(ProjectService projectService,
                             UserDetailsServiceImpl userDetailsService,
                             ConversionService conversionService) {
        this.projectService = projectService;
        this.userDetailsService = userDetailsService;
        this.conversionService = conversionService;
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public Set<Project> getAllProjectsForUser(Principal principal) {
        User user = (User) userDetailsService.loadUserByUsername(principal.getName());
        return user.getProjects();
    }


    @GetMapping("/{id}")
    @JsonView(Views.FullProject.class)
    public Project getProject(@PathVariable("id") Long id) {
        return projectService.getProject(id);
    }

    @PostMapping
    public Project createProject(
            Principal principal,
            @RequestBody ProjectCreateRequest projectCreateRequest
    ) {
        Project project = conversionService
                .convert(projectCreateRequest, Project.class);
        User user = (User)userDetailsService.loadUserByUsername(principal.getName());

        project.getUsers().add(user);
        user.getProjects().add(project);

        projectService.saveProject(project);

        return project;
    }
}
