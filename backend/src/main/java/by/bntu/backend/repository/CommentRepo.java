package by.bntu.backend.repository;

import by.bntu.backend.domain.Comment;
import by.bntu.backend.domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepo extends JpaRepository<Comment, Long> {

    List<Comment> findAllByTaskId(Long taskId);
}
