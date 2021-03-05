package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.domain.VerificationToken;
import be.intecbrussel.iddblog.email.EmailService;
import be.intecbrussel.iddblog.password.RandomPasswordGenerator;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import be.intecbrussel.iddblog.validation.error.UserAlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
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
import java.security.Principal;
import java.util.*;

@Slf4j
@Controller
public class RegisteredVisitorController implements HandlerExceptionResolver {

    private final RegisteredVisitorService registeredVisitorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private final JavaMailSender javaMailSender;

    public RegisteredVisitorController(RegisteredVisitorService registeredVisitorService,
                                        JavaMailSender javaMailSender) {
        this.registeredVisitorService = registeredVisitorService;
        this.javaMailSender = javaMailSender;
    }

    @GetMapping({"/", "/index"})
    public String getIndex(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String loggedinuser = "visitor";
        String idUser = "";

        RegisteredVisitor user = registeredVisitorService.findByUsername(authentication.getName());

        if( user!= null && authentication!=null && !authentication.getName().equals("anonymousUser")) {
            loggedinuser = authentication.getName();
            idUser = user.getId().toString();
        }

        model.addAttribute("loggedinuser", loggedinuser);
        model.addAttribute("idUser", idUser);

        return "index";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logOut(HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/index";
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
        String extension = multipartFile.getOriginalFilename().substring(multipartFile.getOriginalFilename().indexOf(".")+1,multipartFile.getOriginalFilename().length());

        if (bindingResult.hasErrors()) {

            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            log.warn("number of binding errors: " + bindingResult.getAllErrors().size());

            return "registerform";
        }

        if (extension.equals("png") || extension.equals("jpeg") || extension.equals("jpg")) {
            registeredVisitor.setAvatar(Base64.getEncoder().encodeToString(multipartFile.getBytes()));
        }
        else if (extension.isEmpty()){
            registeredVisitor.setAvatar(Base64.getEncoder().encodeToString(defaultFile.getBytes()));
        }
        else
        {
            model.addAttribute("error", "Wrong File extension, please choose png or jpeg file.");

            return "registerform";
        }

        log.warn("multipartFile: " + multipartFile.getBytes());
        registeredVisitor.setAvatar(Base64.getEncoder().encodeToString(multipartFile.getBytes()));

        try {
            registeredVisitor.setEnabled(false);
            savedVisitor = registeredVisitorService.saveVisitor(registeredVisitor);
            registeredVisitorService.createAuthority(registeredVisitor,"USER");

            log.warn("visitor has been saved with id: " + savedVisitor.getId());
        } catch (UserAlreadyExistException uaeEx) {

            model.addAttribute("message", "An account for that username/email already exists.");

            return "registerform";
        } catch (RuntimeException re) {
            log.warn("I am in the runtime exception.");
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
    public String updateRegisteredVisitor(@PathVariable String id, Model model) {
        RegisteredVisitor visitor = registeredVisitorService.findById(Long.valueOf(id));

        // DEFAULT_PWD set to password and confirmPassword and used in thymeleaf if the password is not changed
        // reason is to pass the validations (password not null and password matches confirmPassword)
        RandomPasswordGenerator passGen = new RandomPasswordGenerator();
        final String DEFAULT_PWD = passGen.generatePassayPassword();
        visitor.setPassword(DEFAULT_PWD);
        visitor.setConfirmPassword(DEFAULT_PWD);

        log.warn("id getupdateRegisteredVisitor: " + id);

        model.addAttribute("registeredvisitor", visitor);

        return "updateprofile";
    }

    @PostMapping("registeredvisitor/edit/{id}")
    public String UpdateRegisteredVisitor(@PathVariable("id") long id, @ModelAttribute("registeredvisitor") @Valid RegisteredVisitor visitor
            , BindingResult bindingResult, Model model) {

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

        return "redirect:/registeredvisitor/" + id + "/show";
    }

    @GetMapping("registeredvisitor/update password/{id}")
    public String updatePwdRegisteredVisitor(@PathVariable long id, Model model) {

        RegisteredVisitor user = registeredVisitorService.findById(id);

        model.addAttribute("registeredvisitor", user);

        log.warn("id from getupdatePwdRegisteredVisitor: " + user.getId());

        return "changepassword";
    }


    @PostMapping("registeredvisitor/edit password/{id}")
    public String UpdatePwdRegisteredVisitor(@PathVariable("id") long id, @ModelAttribute("registeredvisitor") @Valid RegisteredVisitor visitor
            ,BindingResult bindingResult, Model model) {

        log.warn("pwd from postUpdatePwd: " + visitor.getPassword());
        log.warn("oldPwd from postUpdatePwd: " + visitor.getOldPassword());
        log.warn("confirmPwd from postUpdatePwd: " + visitor.getConfirmPassword());

        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(objectError -> log.warn(objectError.toString()));
            return "changepassword";
        }


        RegisteredVisitor userDb = registeredVisitorService.findById(id);


        if (!registeredVisitorService.checkIfValidOldPassword(userDb, visitor.getOldPassword())) {
            log.warn("the old password is not correct.");
            model.addAttribute("messageInvalidOldPwd", "The old password is invalid.");
            return "changepassword";
        }

        log.warn("validation on old password is ok.");

        registeredVisitorService.updateUserPwd(id, visitor.getPassword());

        return "redirect:/index";
    }

    @RequestMapping(value = {"/adminpage"}, method = RequestMethod.GET)
    public ModelAndView adminPage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("adminpage");

        return model;
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
        logout(request,response);

        return "redirect:/index";
    }

    @GetMapping("/forgetPassword")
    public String showForgetPassword( Model model) {
        log.warn("Your are in forget password:");
        return "/forgetPassword";
    }

    @PostMapping("/forgetPassword")
    public String resetPassword(HttpServletRequest request, Model model) {

        String userEmail = request.getParameter("email");
        RegisteredVisitor visitor = registeredVisitorService.findByEmailAddress(userEmail);

        if(visitor == null) {
            model.addAttribute("msgEmailMissing", "The email address you have entered does not exist in our database.");
            return "/forgetPassword";
        }

        String appUrl = request.getContextPath();
        String token = UUID.randomUUID().toString();
        registeredVisitorService.createVerificationToken(visitor, token);

        String recipientAddress = visitor.getEmailAddress();
        String subject = "Reset password link";
        String confirmationUrl
                = "http://localhost:8080" + appUrl + "/resetPwdConfirm?token=" + token;
        String message = "Dear,\n\nTo reset your account, please click on the following link: " + confirmationUrl +
                ".\n\nKind Regards,\nThe Blog Post Team";

        emailService.sendSimpleMessage(recipientAddress, subject, message);

        return "/email-sent";
    }

    @GetMapping("/resetPwdConfirm")
    public String confirmResetPwd(@RequestParam("token") String token, Model model) {

        VerificationToken verificationToken = registeredVisitorService.getVerificationToken(token);
        if (verificationToken == null) {
            return "redirect:/forbidden-page";
        }

        RegisteredVisitor visitor = verificationToken.getRegisteredVisitor();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {
            return "redirect:/forbidden-page";
        }

        model.addAttribute("registeredvisitor",visitor);

        return "/reset-pwd";
    }

    @PostMapping("/reset-pwd")
    public String resetPwd(@ModelAttribute("registeredvisitor") @Valid RegisteredVisitor visitor, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            log.debug("Error in bindingResult!!!");
            return "reset-pwd";
        }

        registeredVisitorService.updateUserPwd(visitor.getId(), visitor.getPassword());

        return "redirect:/pwd-reset-success";
    }

    @GetMapping("/pwd-reset-success")
    public String showResetPwdSuccess() {
        return "pwd-reset-success";
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest httpServletRequest,
                                         HttpServletResponse httpServletResponse,
                                         Object o, Exception e) {

        e.printStackTrace();
        log.warn("I am in the resolveException");

        Map<String, Object> model = new HashMap<String, Object>();
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


}