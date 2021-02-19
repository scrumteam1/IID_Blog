package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import be.intecbrussel.iddblog.validation.error.UserAlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@Controller
public class RegisteredVisitorController {

    private final RegisteredVisitorService registeredVisitorService;

    public RegisteredVisitorController(RegisteredVisitorService registeredVisitorService) {
        this.registeredVisitorService = registeredVisitorService;
    }

    @GetMapping("registeredvisitor/new")
    public String newMember(Model model){
        model.addAttribute("registeredvisitor", new RegisteredVisitor());

        return "registerform";
    }

    @PostMapping("registeredvisitor")
    public String save(@ModelAttribute("registeredvisitor") @Valid RegisteredVisitor registeredVisitor, BindingResult bindingResult
            , Model model){

        RegisteredVisitor savedVisitor;

        if(bindingResult.hasErrors()){

            bindingResult.getAllErrors().forEach(objectError -> log.debug(objectError.toString()));

            return "registerform";
        }

        try {
             savedVisitor = registeredVisitorService.saveVisitor(registeredVisitor);
        } catch (UserAlreadyExistException uaeEx) {

            model.addAttribute("message", "An account for that username/email already exists.");

            return "registerform";
        }

        return "redirect:/registeredvisitor/"+ savedVisitor.getId() +"/show";
    }

    @GetMapping("registeredvisitor/{id}/show")
    public String showById (@PathVariable String id, Model model) {
        model.addAttribute("registeredvisitor", registeredVisitorService.findById(Long.valueOf(id)));
        return "profileview";
    }

    @GetMapping
    @RequestMapping("registeredvisitor/{id}/update")
    public String updateRegisteredVisitor (@PathVariable String id, Model model) {
        model.addAttribute("registeredvisitor", registeredVisitorService.findById(Long.valueOf(id)));
        return "profile";
    }

    @PostMapping
    @RequestMapping("updatevisitor")
    public String UpdateRegisteredVisitor(@ModelAttribute RegisteredVisitor visitor){
         registeredVisitorService.updateVisitorWithPwd(visitor.getId(), visitor.getUsername(),
                                            visitor.getFirstName(), visitor.getLastName(), visitor.getEmailAddress(), visitor.getIsWriter());
//        RegisteredVisitor updatedVisitor = registeredVisitorService.findById(visitor.getId());
        return "redirect:/registeredvisitor/show";
    }

}