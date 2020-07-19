package by.bntu.backend.repository;

import by.bntu.backend.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {

    List<Task> findAllByProjectId(Long projectId);
}
