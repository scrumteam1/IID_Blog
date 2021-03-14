package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.service.AuthService;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class AdminController {
    private final RegisteredVisitorService registeredVisitorService;

    private final AuthService authService;

    public AdminController(RegisteredVisitorService registeredVisitorService, AuthService authService) {
        this.registeredVisitorService = registeredVisitorService;
        this.authService = authService;
    }

    @GetMapping("/admin")
    public String showAdmin(Model model) {

        userContext(model);

        List<RegisteredVisitor> users = registeredVisitorService.findAll();
        model.addAttribute("users", users);

        return "/admin/admin";
    }


    @GetMapping("/ban/{id}")
    public String banMember(@PathVariable String id) {

        RegisteredVisitor visitor = registeredVisitorService.findById(Long.valueOf(id));

        registeredVisitorService.updateUserEnabled(visitor, !visitor.isEnabled());

        return "redirect:/admin";
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
            isAdmin = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
            isWriter = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("WRITER"));
            isRegistered = authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("USER"));
        }

        model.addAttribute("loggedinuser", loggedinuser);
        model.addAttribute("idUser", idUser);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isWriter", isWriter);
        model.addAttribute("isRegistered", isRegistered);
    }
}
