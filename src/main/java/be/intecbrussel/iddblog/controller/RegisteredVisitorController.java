package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.password.RandomPasswordGenerator;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import be.intecbrussel.iddblog.validation.error.UserAlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.io.IOException;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Controller
public class RegisteredVisitorController implements HandlerExceptionResolver {

    private final RegisteredVisitorService registeredVisitorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public RegisteredVisitorController(RegisteredVisitorService registeredVisitorService) {
        this.registeredVisitorService = registeredVisitorService;
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

        if (bindingResult.hasErrors()) {

            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));
            log.warn("number of binding errors: " + bindingResult.getAllErrors().size());

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

    @GetMapping("/delete/{id}")
    public String deleteRegisteredVisitor(@PathVariable("id") long id) {
        RegisteredVisitor registeredVisitor = registeredVisitorService.findById(id);
        registeredVisitorService.deleteVisitor(registeredVisitor.getUsername());

        return "redirect:/index";
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
}