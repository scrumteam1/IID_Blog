package be.intecbrussel.iddblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.security.RolesAllowed;

@Controller
public class LoginController {

    @RolesAllowed({"USER", "ADMIN"})
    @RequestMapping("/index")
    public String getUser(){
        return "welcome to the user page.";
    }

    @RolesAllowed("ADMIN")
    @RequestMapping("/admin")
    public String getAdmin(){
        return "admin page.";
    }

}
