package be.intecbrussel.iddblog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class notyetcontroller {

        @GetMapping({"notyet"})
        public String notyet() {

            return "notyet";
        }
    }

