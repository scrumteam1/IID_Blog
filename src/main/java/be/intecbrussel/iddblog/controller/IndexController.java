package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.service.AuthService;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private final RegisteredVisitorService registeredVisitorService;

    private final AuthService authService;


    public IndexController(RegisteredVisitorService registeredVisitorService, AuthService authService) {
        this.registeredVisitorService = registeredVisitorService;
        this.authService = authService;
    }

    @RequestMapping("/login")
    public String login(){

        return "login";
    }
    @RequestMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("loginError", true);

       return "login";
    }

    @GetMapping({"/", "/index"})
    public String getIndex(Model model) {

        userContext(model);

        return "index";
    }

    @GetMapping("/about")
    public String showAbout(Model model) {

        userContext(model);

        return "/about";
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
            String authority = authService.findAuthorityByUsername(user.getUsername());
            isAdmin = authority.equals("ADMIN");
            isWriter = authority.equals("WRITER");
            isRegistered = authority.equals("USER");
        }

        model.addAttribute("loggedinuser", loggedinuser);
        model.addAttribute("idUser", idUser);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isWriter", isWriter);
        model.addAttribute("isRegistered", isRegistered);
    }
}
