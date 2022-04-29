package com.cw.cwSpring.Controllers;

import com.cw.cwSpring.Services.CustomUserDetails;
import com.cw.cwSpring.Services.CustomUserDetailsService;
import com.cw.cwSpring.models.*;
import com.cw.cwSpring.repo.*;
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
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    WinnerRepository winnerRepository;
    @Autowired
    UserDataRepository userDataRepository;

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
        Iterable<Member> members = memberRepository.findMembersByTenderID(TenderId);
        model.addAttribute("members",members);
        model.addAttribute("tender",tenderArray);
        return "home-details";
    }
    @GetMapping("/account")
    public String account(Model model) {
        //model.addAttribute("name", "Hello!");
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserData userData = new UserData();
        if(userDataRepository.findUserDataByUserID(user.getID()) == null) {

        }
        else {
            userData = userDataRepository.findUserDataByUserID(user.getID());
        }
        model.addAttribute("el",userData);
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
        if(!password.equals(passwordSecond)) {
            model.addAttribute("passNotEquals",true);
            return "registration";
        }
        else {
            if(userRepository.findByUsername(login)==null) {
                User user = new User(login,encodPass);
                userRepository.save(user);
                return "login";
            }
            else {
                model.addAttribute("userExist",true);
                return "registration";
            }
        }
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
        return "redirect:/myTenders";
    }
    @PostMapping("/saveUserData")
    public String saveUserData(@RequestParam String phone, @RequestParam String address,@RequestParam String name,Model model) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserData userData = new UserData();
        if(userDataRepository.findUserDataByUserID(user.getID()) == null) {
            userData.userID = user.getID();
        }
        else {
            userData = userDataRepository.findUserDataByUserID(user.getID());
        }
        userData.setName(name);
        userData.setAddress(address);
        userData.setPhone(phone);
        userDataRepository.save(userData);
        return "redirect:/account";
    }
    @GetMapping("/home")
    public String loadHomePage(Model model) {
        Iterable<Tender> tenders = tenderRepository.findTendersByIsActive(true);
        model.addAttribute("tenders",tenders);
        return "home";
    }
    @GetMapping("/myFinish")
    public String myFinishPage(Model model) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Iterable<Tender> tenders = tenderRepository.findTendersByUserIDAndIsActive(user.getID(),false);
        model.addAttribute("tenders",tenders);
        return "myFinish";
    }
    @GetMapping("/myFinish/{TenderId}")
    public String myFinishDetails(@PathVariable(value = "TenderId") Integer TenderId, Model model) {
        Optional<Tender> tender = tenderRepository.findById(TenderId);
        ArrayList<Tender> tenderArray = new ArrayList<>();
        tender.ifPresent(tenderArray::add);
        Iterable<Member> members = memberRepository.findMembersByTenderID(TenderId);
        model.addAttribute("members",members);
        model.addAttribute("tender",tenderArray);
        Member member = memberRepository.getWinnerByTenderID(TenderId);
        model.addAttribute("winner",member);
        if(member!=null) {
            model.addAttribute("winnerData",userDataRepository.findUserDataByUserID(member.getUserID()));
        }
        return "myFinish-details";
    }
    @GetMapping("/myOffers")
    public String loadMyOffers(Model model) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("tenders",tenderRepository.getAllTenderByOfferUserID(user.getID()));
        return "myOffers";
    }
    @GetMapping("/myTenders")
    public String loadMyTendersPage(Model model) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Iterable<Tender> tenders = tenderRepository.findTendersByUserIDAndIsActive(user.getID(),true);
        model.addAttribute("tenders",tenders);
        return "myTenders";
    }
    @GetMapping("/myTenders/{TenderId}")
    public String myTenderDetails(@PathVariable(value = "TenderId") Integer TenderId, Model model) {
        Optional<Tender> tender = tenderRepository.findById(TenderId);
        ArrayList<Tender> tenderArray = new ArrayList<>();
        tender.ifPresent(tenderArray::add);
        Iterable<Member> members = memberRepository.findMembersByTenderID(TenderId);
        model.addAttribute("members",members);
        model.addAttribute("tender",tenderArray);
        return "myTenders-details";
    }
    @GetMapping("/myTenders/remove/{TenderId}")
    public String removeMyTender(@PathVariable(value = "TenderId") Integer TenderId, Model model) {
        tenderRepository.deleteById(TenderId);
        return "redirect:/myTenders";
    }
    @PostMapping("/takePart")
    public String addMember(@RequestParam Integer TenderID, @RequestParam String description,@RequestParam Integer term,@RequestParam Integer price,Model model) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(memberRepository.findMembersByTenderIDAndUserID(TenderID,user.getID()).size() == 0) {
            Member member = new Member(user.getID(),TenderID,description,price,term);
            memberRepository.save(member);
        }
        return "redirect:/home";
    }
    @GetMapping("/myTenders/finish/{TenderId}")
    public String finishMyTender(@PathVariable(value = "TenderId") Integer TenderId, Model model) {
        Tender tender = tenderRepository.findTenderById(TenderId);
        tender.setActive(false);
        tenderRepository.save(tender);
        Iterable<Member> members = memberRepository.findMembersByTenderID(TenderId);
        Member bestMember = new Member();
        for(Member item : members)
        {
            if(bestMember.getOfferPrice() != null) {
                if(bestMember.getOfferPrice() > item.getOfferPrice()) {
                    bestMember = item;
                }
            }
            else {
                bestMember = item;
            }
        }
        Winner winner = new Winner();
        winner.setMemberID(bestMember.getId());
        winnerRepository.save(winner);
        return "redirect:/myTenders";
    }
}
