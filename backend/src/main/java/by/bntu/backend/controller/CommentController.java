package by.bntu.backend.controller;

import by.bntu.backend.domain.Comment;
import by.bntu.backend.domain.Task;
import by.bntu.backend.domain.Views;
import by.bntu.backend.service.CommentService;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("projects/{projectId}/tasks/{taskId}/comments")
    @JsonView(Views.FullComment.class)
    public List<Comment> getAllCommentForTask(@PathVariable("taskId") Long taskId) {
        return commentService.getAllCommentsFotTask(taskId);
    }
}
