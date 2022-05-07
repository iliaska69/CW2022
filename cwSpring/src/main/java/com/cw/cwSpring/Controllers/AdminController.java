package com.cw.cwSpring.Controllers;

import com.cw.cwSpring.Services.Interfaces.AdminService;
import com.cw.cwSpring.Services.Interfaces.UserDataService;
import com.cw.cwSpring.Services.Interfaces.UserService;
import com.cw.cwSpring.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class AdminController {

    @Autowired
    UserService userService;

    @Autowired
    UserDataService userDataService;

    @Autowired
    AdminService adminService;

    @GetMapping("/admin")
    public String adminPanel(Model model) {
        model.addAttribute("users",userService.GetAllUsers());
        model.addAttribute("userData",userDataService.GetAllUserData());
        return "adminPage";
    }
    @GetMapping("/admin/removeUser/{TenderId}")
    public String RemoveUser(@PathVariable(value = "TenderId") Integer TenderId, Model model) {
        adminService.RemoveUserBuId(TenderId);
        return "redirect:/admin";
    }
}
