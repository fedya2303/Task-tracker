package by.bntu.backend.controller;

import by.bntu.backend.domain.Project;
import by.bntu.backend.domain.User;
import by.bntu.backend.domain.Views;
import by.bntu.backend.exception.NotFoundResourceException;
import by.bntu.backend.repository.UserRepo;
import by.bntu.backend.service.ProjectService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserRepo userRepo;
    private ProjectService projectService;

    @Autowired
    public UserController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping("/usercreds")
    @JsonView(Views.FullProfile.class)
    public User getUserCreds(Principal principal) {
        User byUsername = userRepo.findByUsername(principal.getName());
        return byUsername;
    }

    @GetMapping("/users")
    @JsonView(Views.FullProfile.class)
    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    @PostMapping("/projects/{projectId}/users")
    public void setUsersForProject(
            @PathVariable("projectId") Long projectId,
            @RequestBody List<Long> usersId
    ) {
        List<User> usersInProject = userRepo.findAllById(usersId);
        Project project = projectService.getProject(projectId);
        List<User> allUsers = userRepo.findAll();

        allUsers.removeAll(usersInProject);
        allUsers.forEach(user ->
                user.getProjects().remove(project));

        project.getUsers().addAll(usersInProject);
        usersInProject.forEach(user ->
                user.getProjects().add(project));

        userRepo.saveAll(usersInProject);
        userRepo.saveAll(allUsers);
    }

    @GetMapping("/projects/{projectId}/participants/{userId}")
    public boolean isParticipant(
            @PathVariable("projectId") Long projectId,
            @PathVariable("userId") Long userId
    ) {
        User user = userRepo.findById(userId).orElseThrow(NotFoundResourceException::new);
        boolean b = user.getProjects().stream()
                .anyMatch(project -> project.getId().equals(projectId));
        return b;
    }
}
