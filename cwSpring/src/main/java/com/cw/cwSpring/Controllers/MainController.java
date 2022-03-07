package com.cw.cwSpring.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/authorization")
    public String authorization(Model model) {
        //model.addAttribute("name", "Hello!");
        return "authorization";
    }
    @GetMapping("/registration")
    public String registration(Model model) {
        //model.addAttribute("name", "Hello!");
        return "registration";
    }

}
