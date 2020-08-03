package by.bntu.backend.service;

import by.bntu.backend.domain.Project;
import by.bntu.backend.exception.NotFoundResourceException;
import by.bntu.backend.repository.ProjectRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    private final ProjectRepo projectRepo;

    @Autowired
    public ProjectService(ProjectRepo projectRepo) {
        this.projectRepo = projectRepo;
    }

    public List<Project> getAllProjects() {
        return projectRepo.findAll();
    }

    public Project getProject(Long id) {
        Optional<Project> project = projectRepo.findById(id);
        return project.orElseThrow(NotFoundResourceException::new);
    }

    public List<Project> getAllProjectsForUser(String name) {
        return projectRepo.findAllByUsersIsLike(name);
    }

    public void saveProject(Project project) {
        projectRepo.save(project);
        projectRepo.flush();
    }

    public Project updateProject(Project project) {
        Project save = projectRepo.save(project);
        projectRepo.flush();
        return save;
    }
}
