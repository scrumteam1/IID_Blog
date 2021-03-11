package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.password.RandomPasswordGenerator;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import be.intecbrussel.iddblog.validation.error.UserAlreadyExistException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UpdateVisitorController {

    private final RegisteredVisitorService registeredVisitorService;

    public UpdateVisitorController(RegisteredVisitorService registeredVisitorService) {
        this.registeredVisitorService = registeredVisitorService;
    }

    @GetMapping("registeredvisitor/update/{id}")
    public String updateRegisteredVisitor(@PathVariable String id, Model model) {
        RegisteredVisitor visitor = registeredVisitorService.findById(Long.valueOf(id));

        // DEFAULT_PWD set to password and confirmPassword and used in thymeleaf if the password is not changed
        // reason is to pass the validations (password not null and password matches confirmPassword)
        RandomPasswordGenerator passGen = new RandomPasswordGenerator();
        final String DEFAULT_PWD = passGen.generatePassayPassword();
        visitor.setPassword(DEFAULT_PWD);
        visitor.setConfirmPassword(DEFAULT_PWD);

        model.addAttribute("registeredvisitor", visitor);

        return "updateprofile";
    }

    @PostMapping("registeredvisitor/edit/{id}")
    public String UpdateRegisteredVisitor(@PathVariable("id") long id, @ModelAttribute("registeredvisitor") @Valid RegisteredVisitor visitor
            , BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {

            return "updateprofile";
        }

        try {
            registeredVisitorService.updateVisitorWithoutPwd(visitor);

        } catch (UserAlreadyExistException uaeEx) {

            model.addAttribute("message", "An account for that username/email already exists.");

            return "updateprofile";
        }

        return "redirect:/registeredvisitor/" + id + "/show";
    }

    @GetMapping("registeredvisitor/update password/{id}")
    public String updatePwdRegisteredVisitor(@PathVariable long id, Model model) {

        RegisteredVisitor user = registeredVisitorService.findById(id);

        model.addAttribute("registeredvisitor", user);

        return "/password/changepassword";
    }


    @PostMapping("registeredvisitor/edit password/{id}")
    public String UpdatePwdRegisteredVisitor(@PathVariable("id") long id, @ModelAttribute("registeredvisitor") @Valid RegisteredVisitor visitor
            , BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "/password/changepassword";
        }


        RegisteredVisitor userDb = registeredVisitorService.findById(id);


        if (!registeredVisitorService.checkIfValidOldPassword(userDb, visitor.getOldPassword())) {
            model.addAttribute("messageInvalidOldPwd", "The old password is invalid.");
            return "/password/changepassword";
        }

        registeredVisitorService.updateUserPwd(id, visitor.getPassword());

        return "redirect:/index";
    }

}
