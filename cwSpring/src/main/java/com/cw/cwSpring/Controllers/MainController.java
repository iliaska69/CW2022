package com.cw.cwSpring.Controllers;

import com.cw.cwSpring.models.Tender;
import com.cw.cwSpring.models.User;
import com.cw.cwSpring.repo.TenderRepository;
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
    @Autowired
    TenderRepository tenderRepository;

    @GetMapping("/login")
    public String authorization(Model model) {
        //model.addAttribute("name", "Hello!");
        return "login";
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
        return "login";
    }
    @GetMapping("/addTender")
    public String loadTenderPage(Model model) {
        //model.addAttribute("name", "Hello!");
        return "addTender";
    }
    @PostMapping("/addTender")
    public String addTender(@RequestParam String name, @RequestParam String description,@RequestParam Integer term,@RequestParam Integer price,Model model) {
        Tender tender = new Tender(name,description,term,price);
        tenderRepository.save(tender);
        return "addTender";
    }
    @GetMapping("/home")
    public String loadHomePage(Model model) {
        Iterable<Tender> tenders = tenderRepository.findAll();
        model.addAttribute("tenders",tenders);
        return "home";
    }
}
