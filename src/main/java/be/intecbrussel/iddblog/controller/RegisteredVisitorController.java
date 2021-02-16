package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.domain.RegisteredVisitor;
import be.intecbrussel.iddblog.service.RegisteredVisitorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    public String save(@ModelAttribute("registeredvisitor") @Valid RegisteredVisitor registeredVisitor, BindingResult bindingResult){

        if(bindingResult.hasErrors()){

            bindingResult.getAllErrors().forEach(objectError -> {
                log.debug(objectError.toString());
            });

            return "registerform";
        }

        RegisteredVisitor savedVisitor = registeredVisitorService.saveVisitor(registeredVisitor);

        return "redirect:/index";
    }
}
