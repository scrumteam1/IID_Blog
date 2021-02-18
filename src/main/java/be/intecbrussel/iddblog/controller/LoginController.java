package be.intecbrussel.iddblog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
public class LoginController {



    @RolesAllowed({"USER", "ADMIN"})
    @RequestMapping("/*")
    public String getUser(){
        return "login";
    }

    @RolesAllowed("ADMIN")
    @RequestMapping("/admin")
    public String getAdmin(){
        return "login";
    }

}
