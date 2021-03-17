package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.Authority;
import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.domain.WriterPost;
import be.intecbrussel.iddblog.service.AuthService;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import be.intecbrussel.iddblog.service.WriterService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Controller
public class IndexController {

    private final RegisteredVisitorService registeredVisitorService;

    private final AuthService authService;

    private final WriterService writerService;

    public IndexController(RegisteredVisitorService registeredVisitorService, AuthService authService, WriterService writerService) {
        this.registeredVisitorService = registeredVisitorService;
        this.authService = authService;
        this.writerService = writerService;
    }

    @RequestMapping("/login")
    public String login() {

        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);

        return "login";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/index";
    }

    @GetMapping({"/", "/index"})
    public String getIndex(Model model) {
        userContext(model);
        List<WriterPost> writerPostList = writerService.findOrderByCreationDate(Calendar.getInstance().getTime());
        if (writerPostList.size() >6) {
            model.addAttribute("posts", writerPostList.subList(0, 6));
        }
        else model.addAttribute("posts", writerPostList);

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
