package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.Authority;
import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.service.AuthService;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String showAdmin(Model model,@Param("keyword") String keyword) {

        return listAdminByPage(model,keyword ,1, "username", "asc");
    }

    @RequestMapping("/admin/page/{pageNumber}")
    public String listAdminByPage(Model model, @Param("keyword") String keyword,
                                  @PathVariable(name = "pageNumber") int currentPage,
                                  @Param("sortField") String sortField,
                                  @Param("sortDir") String sortDir) {

        userContext(model);

        Page<RegisteredVisitor> page = registeredVisitorService.findAll(keyword, currentPage,sortField, sortDir);
        List<RegisteredVisitor> users = page.getContent();
        int totalItems = page.getNumberOfElements();
        int totalPages = page.getTotalPages();

        model.addAttribute("users", users);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("reverseSortDir", reverseSortDir);


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
