package com.cw.cwSpring.Controllers;

import com.cw.cwSpring.Services.CustomUserDetails;
import com.cw.cwSpring.Services.Implementation.TenderServiceImp;
import com.cw.cwSpring.Services.TenderSortService;
import com.cw.cwSpring.models.Tender;
import com.cw.cwSpring.repo.TenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class TenderController {

    @Autowired
    TenderServiceImp tenderServiceImp;

    @GetMapping("/home")
    public String loadHomePage(Model model) {
        model.addAttribute("tenders",tenderServiceImp.GetAllActiveTenders());
        return "home";
    }

    @PostMapping("/home")
    public String tenderSort(@RequestParam String name, @RequestParam String status, @RequestParam Integer priceFrom, @RequestParam Integer priceTo, Model model) {
        model.addAttribute("tenders",tenderServiceImp.GetAllActiveTendersUsingSort(name,status,priceFrom,priceTo));
        return "home";
    }

    @GetMapping("/addTender")
    public String loadTenderPage(Model model) {
        return "addTender";
    }

    @PostMapping("/addTender")
    public String addTender(@RequestParam String name, @RequestParam String description,@RequestParam Integer term,@RequestParam Integer price,Model model) {
        tenderServiceImp.SaveTender(new Tender(name,description,term,price,0));
        return "redirect:/myTenders";
    }
}
