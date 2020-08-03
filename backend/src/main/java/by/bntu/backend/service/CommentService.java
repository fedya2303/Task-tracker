package by.bntu.backend.service;

import by.bntu.backend.domain.Comment;
import by.bntu.backend.repository.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepo commentRepo;

    @Autowired
    public CommentService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    public List<Comment> getAllCommentsFotTask(Long taskId) {
        return commentRepo.findAllByTaskId(taskId);
    }

    public Comment save(Comment comment) {
        return commentRepo.save(comment);
    }
}
