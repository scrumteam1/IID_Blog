package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.password.RandomPasswordGenerator;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import be.intecbrussel.iddblog.validation.error.UserAlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class RegisteredVisitorController implements HandlerExceptionResolver {

    private final RegisteredVisitorService registeredVisitorService;

    public RegisteredVisitorController(RegisteredVisitorService registeredVisitorService) {
        this.registeredVisitorService = registeredVisitorService;
    }

    @GetMapping("registeredvisitor/new")
    public String newMember(Model model) {
        model.addAttribute("registeredvisitor", new RegisteredVisitor());

        return "registerform";
    }

    @PostMapping("registeredvisitor")
    public String save(@ModelAttribute("registeredvisitor") @Valid RegisteredVisitor registeredVisitor, @RequestParam("image") MultipartFile multipartFile, BindingResult bindingResult
            , Model model) throws IOException {
        RegisteredVisitor savedVisitor;

        if (bindingResult.hasErrors()) {

            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return "registerform";
        }

        registeredVisitor.setAvatar(Base64.getEncoder().encodeToString(multipartFile.getBytes()));

        try {
            savedVisitor = registeredVisitorService.saveVisitor(registeredVisitor);
        } catch (UserAlreadyExistException uaeEx) {

            model.addAttribute("message", "An account for that username/email already exists.");

            return "registerform";
        }

        return "redirect:/registeredvisitor/" + savedVisitor.getId() + "/show";
    }

    @GetMapping("registeredvisitor/{id}/show")
    public String showById(@PathVariable String id, Model model) {
        model.addAttribute("registeredvisitor", registeredVisitorService.findById(Long.valueOf(id)));
        return "profileview";
    }

    @GetMapping("registeredvisitor/update/{id}")
    public String updateRegisteredVisitor(@PathVariable long id, Model model) {

        // DEFAULT_PWD set to password and confirmPassword and used in thymeleaf if the password is not changed
        // reason is to pass the validations (password not null and password matches confirmPassword)
        RandomPasswordGenerator passGen = new RandomPasswordGenerator();
        final String DEFAULT_PWD = passGen.generatePassayPassword();

        model.addAttribute("registeredvisitor", registeredVisitorService.findById(id));
        model.addAttribute("defaultPwd", DEFAULT_PWD);

        return "updateprofile";
    }

    @PostMapping("registeredvisitor/edit/{id}")
    public String UpdateRegisteredVisitor(@PathVariable("id") long id, @ModelAttribute("registeredvisitor") @Valid RegisteredVisitor visitor
            ,BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return "updateprofile";
        }

        try {
            registeredVisitorService.updateVisitorWithoutPwd(visitor);

        } catch (UserAlreadyExistException uaeEx) {

            model.addAttribute("message", "An account for that username/email already exists.");

            return "updateprofile";
        }

        log.info(visitor.getPassword());
        log.info(visitor.getConfirmPassword());
        log.info(visitor.getEncodedPassword());

        return "redirect:/registeredvisitor/"+id+"/show";
    }


    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) {
        Map<String, Object> model = new HashMap<String, Object>();
        if (e instanceof MaxUploadSizeExceededException)
        {
            model.put("error", "Please choose a valid picture/size (450KB Maximum)");
            model.put("registeredvisitor", new RegisteredVisitor());
        } else
        {
            model.put("error", "Unexpected error: " + e.getMessage());
        }

        return new ModelAndView("registerform", model);
    }
}