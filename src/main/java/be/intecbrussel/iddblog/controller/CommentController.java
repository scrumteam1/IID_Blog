package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.Comment;
import be.intecbrussel.iddblog.service.CommentService;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import be.intecbrussel.iddblog.service.WriterService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CommentController {
    private final CommentService commentService;
    private final WriterService writerService;
    private final RegisteredVisitorService registeredVisitorService;

    public CommentController(CommentService commentService, WriterService writerService, RegisteredVisitorService registeredVisitorService) {
        this.commentService = commentService;
        this.writerService = writerService;
        this.registeredVisitorService = registeredVisitorService;
    }

    @PostMapping
    public String writeComment(Comment comment){
        commentService.save(comment);
        return "writer/blogpost-view";
    }
}
