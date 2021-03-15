package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.Authority;
import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.domain.VerificationToken;
import be.intecbrussel.iddblog.email.EmailService;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import be.intecbrussel.iddblog.validation.error.UserAlreadyExistException;
import org.springframework.mock.web.MockMultipartFile;
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
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

@Controller
public class RegistrationController implements HandlerExceptionResolver {

    private final RegisteredVisitorService registeredVisitorService;

    private final EmailService emailService;

    public RegistrationController(RegisteredVisitorService registeredVisitorService, EmailService emailService) {
        this.registeredVisitorService = registeredVisitorService;
        this.emailService = emailService;
    }

    @GetMapping("registeredvisitor/new")
    public String newMember(Model model) {
        model.addAttribute("registeredvisitor", new RegisteredVisitor());

        return "registerform";
    }

    @PostMapping("registeredvisitor")
    public String save(
            @ModelAttribute("registeredvisitor") @Valid RegisteredVisitor registeredVisitor,
            BindingResult bindingResult,
            @RequestParam("image") MultipartFile multipartFile,
            Model model) throws IOException {

        RegisteredVisitor savedVisitor;
        MultipartFile defaultFile = new MockMultipartFile("intec.jpg", new FileInputStream("src/main/resources/static/pictures/intec.jpg"));
        String extension = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().indexOf(".") + 1);

        if (bindingResult.hasErrors()) {

            return "registerform";
        }

        if (extension.equals("png") || extension.equals("jpeg") || extension.equals("jpg")) {
            registeredVisitor.setAvatar(Base64.getEncoder().encodeToString(multipartFile.getBytes()));
        } else if (extension.isEmpty()) {
            registeredVisitor.setAvatar(Base64.getEncoder().encodeToString(defaultFile.getBytes()));
        } else {
            model.addAttribute("error", "Wrong File extension, please choose png or jpeg file.");

            return "registerform";
        }

        Authority authority = new Authority();

        if (registeredVisitor.getIsWriter()) {
            authority.setAuthority("WRITER");
        } else {
            authority.setAuthority("USER");
        }

        List<Authority> authorities = new ArrayList<>();
        authorities.add(authority);
        registeredVisitor.setAuthority(authorities);

        try {

            savedVisitor = registeredVisitorService.saveVisitor(registeredVisitor);

        } catch (UserAlreadyExistException uaeEx) {

            model.addAttribute("message", "An account for that username/email already exists.");

            return "registerform";
        }

        return "redirect:/registeredvisitor/confirm/" + savedVisitor.getId();
    }

    @GetMapping("/registeredvisitor/confirm/{id}")
    public String confirmRegistration(@PathVariable long id) {
        RegisteredVisitor visitor = registeredVisitorService.findById(id);
        String token = UUID.randomUUID().toString();
        registeredVisitorService.createVerificationToken(visitor, token);
        String recipientAddress = visitor.getEmailAddress();
        String subject = "Confirmation of your registration to Intec Blog";
        String confirmationUrl = "http://localhost:8080/registeredvisitor/confirmRegistration?token=" + token;
        String text = "Dear, \n\n To confirm your registration, please use the lin below : \n   " + confirmationUrl + "\n\nKind Regards,\nThe Blog Post Team";
        emailService.sendSimpleMessage(recipientAddress, subject, text);

        return "/email-sent";
    }

    @GetMapping("registeredvisitor/confirmRegistration")
    public String registrationConfirmed(@RequestParam String token) {

        VerificationToken verificationToken = registeredVisitorService.getVerificationToken(token);
        if (verificationToken == null) {

            return "/verification-link-failed";
        }

        RegisteredVisitor visitor = verificationToken.getRegisteredVisitor();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return "/verification-link-failed";
        }
        registeredVisitorService.updateUserEnabled(visitor, true);

        return "redirect:/login";
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse,
                                         Object o, Exception e) {

        e.printStackTrace();

        Map<String, Object> model = new HashMap<>();
        if (e instanceof MaxUploadSizeExceededException) {
            model.put("error", "Please choose a valid picture/size (450KB Maximum)");
            model.put("registeredvisitor", new RegisteredVisitor());
        } else {
            model.put("error", "Unexpected error: " + e.getMessage());
        }

        return new ModelAndView("registerform", model);
    }
}
