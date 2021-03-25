package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.Comment;
import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.domain.WriterPost;
import be.intecbrussel.iddblog.service.CommentService;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import be.intecbrussel.iddblog.service.WriterService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

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

    @PostMapping("/writer/post/{id}")
    public String writeComment(@PathVariable("id") long id, @RequestParam(value = "comment", required = false) String content) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        RegisteredVisitor authVisitor = registeredVisitorService.findByUsername(auth.getName());
        if(authVisitor == null){
            return "redirect:/login";
        }
        WriterPost thisPost = writerService.findById(id);
        LocalDateTime commentTime = LocalDateTime.now();

        if(content != null){
            Comment commentToSave = new Comment();
            commentToSave.setRegisteredVisitor(authVisitor);
            commentToSave.setContent(content);
            commentToSave.setWriterPost(thisPost);
            commentToSave.setCreationDate(commentTime);
            commentService.save(commentToSave);
        }

        return "redirect:/writer/post/" + id;
    }
}