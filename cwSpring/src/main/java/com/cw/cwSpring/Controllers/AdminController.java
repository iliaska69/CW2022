package com.cw.cwSpring.Controllers;

import com.cw.cwSpring.Services.Interfaces.UserDataService;
import com.cw.cwSpring.Services.Interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    UserDataService userDataService;

    @GetMapping("/admin")
    public String adminPanel(Model model) {
        model.addAttribute("users",userService.GetAllUsers());
        model.addAttribute("userData",userDataService.GetAllUserData());
        return "adminPage";
    }
}
