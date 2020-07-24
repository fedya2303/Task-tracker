package by.bntu.backend.controller;

import by.bntu.backend.domain.Project;
import by.bntu.backend.domain.User;
import by.bntu.backend.domain.Views;
import by.bntu.backend.dto.ProjectRequestDto;
import by.bntu.backend.service.ProjectService;
import by.bntu.backend.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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
    @Transactional
    public Project getProject(@PathVariable("id") Long id) {
        return projectService.getProject(id);
    }

    @PostMapping
    @Transactional
    public Project createProject(
            Principal principal,
            @RequestBody ProjectRequestDto projectRequestDto
    ) {
        Project project = conversionService
                .convert(projectRequestDto, Project.class);
        User user = (User)userDetailsService.loadUserByUsername(principal.getName());

        project.getUsers().add(user);
        user.getProjects().add(project);

        projectService.saveProject(project);

        return project;
    }

    @PutMapping("/{id}")
    @Transactional
    public Project updateProject(
            Principal principal,
            @RequestBody ProjectRequestDto projectRequestDto
    ) {
        User user = (User)userDetailsService.loadUserByUsername(principal.getName());

        List<Project> userProjects = user.getProjects().stream().filter(project ->
                project.getId().equals(projectRequestDto.getId())
        ).collect(Collectors.toList());

        if (!userProjects.isEmpty()) {
            Project project = conversionService.convert(projectRequestDto, Project.class);
            return projectService.updateProject(project);
        }
        return null;
    }
}
