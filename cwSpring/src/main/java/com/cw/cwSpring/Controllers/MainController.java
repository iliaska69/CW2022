package com.cw.cwSpring.Controllers;

import com.cw.cwSpring.models.User;
import com.cw.cwSpring.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

    @Autowired
    UserRepository userRepository;

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
    @PostMapping("/registration")
    public String registrationForm(@RequestParam String login, @RequestParam String password,@RequestParam String passwordSecond,Model model) {
        User user = new User(login,password);
        userRepository.save(user);
        return "authorization";
    }
}
