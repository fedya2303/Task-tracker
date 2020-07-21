package by.bntu.backend.controller;

import by.bntu.backend.domain.Project;
import by.bntu.backend.domain.User;
import by.bntu.backend.domain.Views;
import by.bntu.backend.service.ProjectService;
import by.bntu.backend.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.authentication.UserServiceBeanDefinitionParser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;
    private final UserDetailsServiceImpl userDetailsService;

    @Autowired
    public ProjectController(ProjectService projectService, UserDetailsServiceImpl userDetailsService) {
        this.projectService = projectService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public Set<Project> getAllProjectsForUser(Principal principal) {
        User user = (User)userDetailsService.loadUserByUsername(principal.getName());
        return user.getProjects();
    }

    @GetMapping("/{id}")
    @JsonView(Views.FullProject.class)
    public Project getProject(@PathVariable("id") Long id) {
        return projectService.getProject(id);
    }
}
