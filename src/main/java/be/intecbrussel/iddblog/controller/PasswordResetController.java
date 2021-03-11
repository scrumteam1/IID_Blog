package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.domain.VerificationToken;
import be.intecbrussel.iddblog.email.EmailService;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Calendar;
import java.util.UUID;

@Controller
public class PasswordResetController {

    private final RegisteredVisitorService registeredVisitorService;

    private final EmailService emailService;

    public PasswordResetController(RegisteredVisitorService registeredVisitorService, EmailService emailService) {
        this.registeredVisitorService = registeredVisitorService;
        this.emailService = emailService;
    }

    @GetMapping("/password/forgetPassword")
    public String showForgetPassword() {
        return "/password/forgetPassword";
    }

    @PostMapping("/password/forgetPassword")
    public String resetPassword(HttpServletRequest request, Model model) {

        String userEmail = request.getParameter("email").trim();

        RegisteredVisitor visitor = registeredVisitorService.findByEmailAddress(userEmail);

        if (visitor == null) {
            model.addAttribute("msgEmailMissing", "The email address you have entered does not exist in our database.");
            return "/password/forgetPassword";
        }

        String appUrl = request.getContextPath();
        String token = UUID.randomUUID().toString();
        registeredVisitorService.createVerificationToken(visitor, token);

        String recipientAddress = visitor.getEmailAddress();
        String subject = "Reset password link";
        String confirmationUrl
                = "http://localhost:8080" + appUrl + "/resetPwdConfirm?token=" + token;
        String message = "Dear,\n\nTo reset your account, please click on the following link: " + confirmationUrl +
                "\n\nKind Regards,\nThe Blog Post Team";

        emailService.sendSimpleMessage(recipientAddress, subject, message);

        return "/email-sent";
    }

    @GetMapping("/resetPwdConfirm")
    public String confirmResetPwd(@RequestParam("token") String token, Model model) {

        VerificationToken verificationToken = registeredVisitorService.getVerificationToken(token);
        if (verificationToken == null) {

            return "/verification-link-failed";
        }

        RegisteredVisitor visitor = verificationToken.getRegisteredVisitor();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return "/verification-link-failed";
        }

        model.addAttribute("registeredvisitor", visitor);

        return "/password/reset-pwd";
    }

    @PostMapping("/password/reset-pwd")
    public String resetPwd(@ModelAttribute("registeredvisitor") @Valid RegisteredVisitor visitor, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return "/password/reset-pwd";
        }

        registeredVisitorService.updateUserPwd(visitor.getId(), visitor.getPassword());

        return "redirect:/password/pwd-reset-success";
    }

    @GetMapping("/password/pwd-reset-success")
    public String showResetPwdSuccess() {
        return "/password/pwd-reset-success";
    }
}
