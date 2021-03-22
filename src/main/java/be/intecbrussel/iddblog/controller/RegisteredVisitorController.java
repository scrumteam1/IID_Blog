package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Slf4j
@Controller
public class RegisteredVisitorController {

    private final RegisteredVisitorService registeredVisitorService;

    public RegisteredVisitorController(RegisteredVisitorService registeredVisitorService) {
        this.registeredVisitorService = registeredVisitorService;
    }

    @GetMapping("registeredvisitor/{id}/show")
    public String showById(@PathVariable String id, Model model) {

        RegisteredVisitor registeredVisitor = registeredVisitorService.findById(Long.valueOf(id));

        // if admin then cannot delete his profile
        boolean isAdmin = registeredVisitor.getAuthority().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
        model.addAttribute("isAdmin", isAdmin);

        model.addAttribute("registeredvisitor", registeredVisitor);

        return "registeredVisitor/profileview";
    }

    @GetMapping("/delete/{id}")
    public String deleteRegisteredVisitor(@PathVariable("id") long id, Principal principal, HttpServletRequest request,
                                          HttpServletResponse response) {
        RegisteredVisitor registeredVisitor = registeredVisitorService.findById(id);

        boolean isAdmin = registeredVisitor.getAuthority().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));

        // The user should only access his own data, otherwise redirect to another page
        if (!principal.getName().equals(registeredVisitor.getUsername()) || isAdmin) {
            return "redirect:/forbidden-page";
        }

        registeredVisitorService.deleteVisitor(registeredVisitor.getUsername());

        // to logout after the user has deleted his own account
        logout(request, response);

        return "redirect:/index";
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
    }
}