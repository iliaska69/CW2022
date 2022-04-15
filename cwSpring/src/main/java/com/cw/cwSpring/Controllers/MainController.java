package com.cw.cwSpring.Controllers;

import com.cw.cwSpring.Services.CustomUserDetails;
import com.cw.cwSpring.Services.CustomUserDetailsService;
import com.cw.cwSpring.models.Tender;
import com.cw.cwSpring.models.User;
import com.cw.cwSpring.repo.TenderRepository;
import com.cw.cwSpring.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Optional;

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
    @GetMapping("/home/{TenderId}")
    public String tenderDetails(@PathVariable(value = "TenderId") Integer TenderId, Model model) {
        Optional<Tender> tender = tenderRepository.findById(TenderId);
        ArrayList<Tender> tenderArray = new ArrayList<>();
        tender.ifPresent(tenderArray::add);
        model.addAttribute("tender",tenderArray);
        return "home-details";
    }
    @GetMapping("/account")
    public String account(Model model) {
        //model.addAttribute("name", "Hello!");
        return "account";
    }
    @GetMapping("/registration")
    public String registration(Model model) {
        //model.addAttribute("name", "Hello!");
        return "registration";
    }
    @PostMapping("/registration")
    public String registrationForm(@RequestParam String login, @RequestParam String password,@RequestParam String passwordSecond,Model model) {
        String encodPass = new BCryptPasswordEncoder().encode(password);
        User user = new User(login,encodPass);
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
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Tender tender = new Tender(name,description,term,price,user.getID());
        tenderRepository.save(tender);
        return "addTender";
    }
    @GetMapping("/home")
    public String loadHomePage(Model model) {
        Iterable<Tender> tenders = tenderRepository.findAll();
        model.addAttribute("tenders",tenders);
        return "home";
    }
    @GetMapping("/myTenders")
    public String loadMyTendersPage(Model model) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Iterable<Tender> tenders = tenderRepository.findTendersByUserID(user.getID());
        model.addAttribute("tenders",tenders);
        return "home";
    }
}
