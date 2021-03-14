package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.email.EmailService;
import be.intecbrussel.iddblog.password.RandomPasswordGenerator;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import be.intecbrussel.iddblog.validation.error.UserAlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Slf4j
@Controller
public class UpdateVisitorController {

    private final RegisteredVisitorService registeredVisitorService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

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

        return "registeredVisitor/updateprofile";
    }

    @PostMapping("registeredvisitor/edit/{id}")
    public String updateRegisteredVisitor(@PathVariable("id") long id, @ModelAttribute("registeredvisitor") @Valid RegisteredVisitor visitor,
                                          BindingResult bindingResult, Model model){

        RegisteredVisitor baseVisitor = registeredVisitorService.findById(visitor.getId());

        if (bindingResult.hasErrors()) {

            return "registeredVisitor/updateprofile";
        }

        try {

            registeredVisitorService.updateVisitorWithoutPwd(visitor);
            String recipientAddress = visitor.getEmailAddress();
            String subject = "INTEC Blog - Profile update for " + visitor.getUsername();
            String mailContent = "Your profile changes on INTEC blog were successful. Your profile looks as follows:" +
                    "\nUsername: " + visitor.getUsername() +
                    "\nFirst name: " + visitor.getFirstName() +
                    "\nLast name: " + visitor.getLastName() +
                    "\nEmail: " + visitor.getEmailAddress() +
                    "\nGender: " + visitor.getGender() +
                    "\n\nIf you're not satisfied with the changes, consider going on our website and " +
                    "subscribe to our premium membership.\n\nThanks dude,\n\nIID Blog team";

            if(!baseVisitor.equals(visitor)){
                emailService.sendSimpleMessage(recipientAddress, subject, mailContent);
            }

        } catch (UserAlreadyExistException uaeEx) {

            model.addAttribute("message", "An account for that username/email already exists.");

            return "registeredVisitor/updateprofile";
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
    public String updatePwdRegisteredVisitor(@PathVariable("id") long id, @ModelAttribute("registeredvisitor") @Valid RegisteredVisitor visitor
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
            isAdmin =  user.getAuthority().stream().anyMatch(a -> a.getAuthority().equals("ADMIN"));
            isWriter =  user.getAuthority().stream().anyMatch(a -> a.getAuthority().equals("WRITER"));
            isRegistered = user.getAuthority().stream().anyMatch(a -> a.getAuthority().equals("USER"));
        }

        model.addAttribute("loggedinuser", loggedinuser);
        model.addAttribute("idUser", idUser);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("isWriter", isWriter);
        model.addAttribute("isRegistered", isRegistered);
    }
}