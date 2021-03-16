package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.Authority;
import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.service.AuthService;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import be.intecbrussel.iddblog.service.WriterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Slf4j
@Controller
public class WriterPostController  {

    private final RegisteredVisitorService registeredVisitorService;

    private final AuthService authService;
    private final WriterService writerService;

    public WriterPostController(RegisteredVisitorService registeredVisitorService, AuthService authService, WriterService writerService) {
        this.registeredVisitorService = registeredVisitorService;
        this.authService = authService;
        this.writerService = writerService;
    }

    @GetMapping("/writer/{id}")
    public String showPostsList (@PathVariable Long id, Model model){
        userContext(model);
        model.addAttribute("posts", writerService.findWriterPostsByUserId(id));
        model.addAttribute("username",registeredVisitorService.findById(id).getUsername());
        model.addAttribute("avatar", registeredVisitorService.findById(id).getAvatar());
        return "/writer/writer";
    }
    @GetMapping("/writer/{id}/{title}")
    public String showPost (@PathVariable Long id,@PathVariable String title, Model model){
        userContext(model);
        model.addAttribute("post", writerService.findByTitle(title));
        return "/writer/blogpost-view";
    }
    private void userContext(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String loggedinuser = "visitor";
        String idUser = "";
        boolean isAdmin = false;
        boolean isWriter = false;
        boolean isRegistered = false;

        RegisteredVisitor user = registeredVisitorService.findByUsername(authentication.getName());

        if (user != null && !authentication.getName().equals("anonymousUser")) {
            loggedinuser = authentication.getName();
            idUser = user.getId().toString();
            List<Authority> authorities = authService.findAuthorityByUsername(user.getUsername());
            isAdmin = authorities.stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
            isWriter = authorities.stream().anyMatch(a -> a.getAuthority().equals("WRITER"));
            isRegistered = authorities.stream().anyMatch(a -> a.getAuthority().equals("USER"));
        }

        model.addAttribute("loggedinuser", loggedinuser);
        model.addAttribute("idUser", idUser);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isWriter", isWriter);
        model.addAttribute("isRegistered", isRegistered);
    }
}
