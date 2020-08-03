package by.bntu.backend.repository;

import by.bntu.backend.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepo extends JpaRepository<Project, Long> {

    List<Project> findAllByUsersIsLike(String username);
}
