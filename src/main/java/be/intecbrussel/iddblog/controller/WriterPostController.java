package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.Authority;
import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.domain.WriterPost;
import be.intecbrussel.iddblog.service.AuthService;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import be.intecbrussel.iddblog.service.WriterService;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class WriterPostController {

    private final RegisteredVisitorService registeredVisitorService;

    private final AuthService authService;
    private final WriterService writerService;

    public WriterPostController(RegisteredVisitorService registeredVisitorService, AuthService authService, WriterService writerService) {
        this.registeredVisitorService = registeredVisitorService;
        this.authService = authService;
        this.writerService = writerService;
    }

    @GetMapping("/writer/{id}")
    public String showPostsList(@PathVariable Long id, Model model , @RequestParam(name = "keyword", defaultValue = "") String keyword) {

        return showPostsByPage(id, model, keyword ,1, "creationDate", "desc");
    }

    @RequestMapping("/writer/{id}/page/{pageNumber}")
    public String showPostsByPage(@PathVariable Long id,Model model, @Param("keyword") String keyword,
                                  @PathVariable(name = "pageNumber") int currentPage,
                                  @Param("sortField") String sortField,
                                  @Param("sortDir") String sortDir) {

        userContext(model);

        RegisteredVisitor visitor = registeredVisitorService.findById(id);

        Page<WriterPost> page = writerService.findWriterPostsByRegisteredVisitor(visitor, keyword, currentPage,sortField, sortDir);
        List<WriterPost> posts = page.getContent();
        int totalItems = page.getNumberOfElements();
        int totalPages = page.getTotalPages();

        model.addAttribute("posts", posts);
        model.addAttribute("avatar", visitor.getAvatar());
        model.addAttribute("user", visitor);
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalItems", totalItems);
        model.addAttribute("totalPages", totalPages);
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDir);
        String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
        model.addAttribute("reverseSortDir", reverseSortDir);

        return "/writer/writer";
    }

    @GetMapping("/writer/{id}/{title}")
    public String showPost(@PathVariable String title, Model model) {
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

    @GetMapping("/deletepost/{id}")
    public String deleteWriterPost(WriterPost writerPost) {
        long id = writerPost.getId();
        writerService.deleteById(id);

        return "redirect:/index";
    }
}
