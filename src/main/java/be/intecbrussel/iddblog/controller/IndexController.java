package be.intecbrussel.iddblog.controller;

import be.intecbrussel.iddblog.repository.RegisteredVisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {

    private RegisteredVisitorRepository registeredVisitorRepository;

    @RequestMapping("/login")
    public String login(){

        return "login";
    }
    @RequestMapping("/login-error")
    public String loginError(Model model){
        model.addAttribute("loginError", true);

       return "login";
    }


}
