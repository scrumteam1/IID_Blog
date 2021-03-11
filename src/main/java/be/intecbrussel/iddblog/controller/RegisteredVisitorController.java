package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.service.AuthService;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;
import java.util.*;

@Slf4j
@Controller
public class RegisteredVisitorController implements HandlerExceptionResolver {

    private final RegisteredVisitorService registeredVisitorService;

    private final AuthService authService;

    public RegisteredVisitorController(RegisteredVisitorService registeredVisitorService, AuthService authService) {
        this.registeredVisitorService = registeredVisitorService;
        this.authService = authService;
    }

    @GetMapping("/admin")
    public String showAdmin(Model model) {

        userContext(model);

        List<RegisteredVisitor> users = registeredVisitorService.findAll();
        model.addAttribute("users", users);

        return "/admin";
    }


    @GetMapping("/ban/{id}")
    public String banMember(@PathVariable String id) {

        RegisteredVisitor visitor = registeredVisitorService.findById(Long.valueOf(id));

        registeredVisitorService.updateUserEnabled(visitor, !visitor.isEnabled());

        return "redirect:/admin";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logOut(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/index";
    }

    @GetMapping("registeredvisitor/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute("registeredvisitor", registeredVisitorService.findById(Long.valueOf(id)));
        return "profileview";
    }

    @GetMapping("/delete/{id}")
    public String deleteRegisteredVisitor(@PathVariable("id") long id, Principal principal, HttpServletRequest request,
                                          HttpServletResponse response) {
        RegisteredVisitor registeredVisitor = registeredVisitorService.findById(id);

        // The user should only access his own data, otherwise redirect to another page
        if (!principal.getName().equals(registeredVisitor.getUsername())) {
            return "redirect:/forbidden-page";
        }

        registeredVisitorService.deleteVisitor(registeredVisitor.getUsername());

        // to logout after the user has deleted his own account
        logout(request, response);

        return "redirect:/index";
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse,
                                         Object o, Exception e) {

        e.printStackTrace();
        log.warn("I am in the resolveException");

        Map<String, Object> model = new HashMap<>();
        if (e instanceof MaxUploadSizeExceededException) {
            model.put("error", "Please choose a valid picture/size (450KB Maximum)");
            model.put("registeredvisitor", new RegisteredVisitor());
        } else {
            model.put("error", "Unexpected error: " + e.getMessage());
        }

        return new ModelAndView("registerform", model);
    }

    private void logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
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