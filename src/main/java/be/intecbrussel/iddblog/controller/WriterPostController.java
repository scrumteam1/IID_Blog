package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.security.Principal;

@Slf4j
@Controller
public class WriterPostController  {

    private final RegisteredVisitorService registeredVisitorService;

    public WriterPostController(RegisteredVisitorService registeredVisitorService) {
        this.registeredVisitorService = registeredVisitorService;
    }

    @GetMapping("/writer/{id}")
    public String showPostsList (@PathVariable Long id, Model model, Principal principal){
        if (!principal.getName().equals(registeredVisitorService.findById(id).getUsername())) {
            return "redirect:/forbidden-page";
        }
        model.addAttribute("posts", registeredVisitorService.findWriterPostsByUserId(id));
        model.addAttribute("username",principal.getName());
        model.addAttribute("avatar", registeredVisitorService.findById(id).getAvatar());
        userContext(model);
        return "writer";
    }
    private void userContext(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String loggedinuser = "visitor";
        String idUser = "";
        boolean isAdmin = false;
        boolean isWriter = false;
        boolean isRegistered = false;

        RegisteredVisitor user = registeredVisitorService.findByUsername(authentication.getName());

        if( user!= null && authentication!=null && !authentication.getName().equals("anonymousUser")) {
            loggedinuser = authentication.getName();
            idUser = user.getId().toString();
            isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().contains("ADMIN"));
            isWriter = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().contains("WRITER"));
            isRegistered = authentication.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().contains("USER"));
        }

        model.addAttribute("loggedinuser", loggedinuser);
        model.addAttribute("idUser", idUser);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isWriter", isWriter);
        model.addAttribute("isRegistered", isRegistered);
    }
}
