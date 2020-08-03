package by.bntu.backend.controller;

import by.bntu.backend.domain.Comment;
import by.bntu.backend.domain.Task;
import by.bntu.backend.domain.User;
import by.bntu.backend.domain.Views;
import by.bntu.backend.service.CommentService;
import by.bntu.backend.service.TaskService;
import by.bntu.backend.service.UserDetailsServiceImpl;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
public class CommentController {

    private CommentService commentService;
    private TaskService taskService;
    private UserDetailsServiceImpl userService;

    @Autowired
    public CommentController(CommentService commentService, TaskService taskService, UserDetailsServiceImpl userService) {
        this.commentService = commentService;
        this.taskService = taskService;
        this.userService = userService;
    }

    @GetMapping("/tasks/{taskId}/comments")
    @JsonView(Views.FullComment.class)
    @Transactional
    public List<Comment> getAllCommentForTask(@PathVariable("taskId") Long taskId) {
        return commentService.getAllCommentsFotTask(taskId);
    }

    @PostMapping("/projects/{projectId}/tasks/{taskId}/comments")
    @Transactional
    public Comment addComment(
            @PathVariable("projectId") Long projectId,
            @PathVariable("taskId") Long taskId,
            @RequestBody Comment comment,
            Principal principal
    ) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        Task task = taskService.getTask(taskId);
        comment.setTask(task);
        comment.setAuthor(user);
        return commentService.save(comment);
    }
}
