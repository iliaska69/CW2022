package com.cw.cwSpring.Controllers;

import com.cw.cwSpring.Services.CustomUserDetails;
import com.cw.cwSpring.Services.CustomUserDetailsService;
import com.cw.cwSpring.Services.TenderSortService;
import com.cw.cwSpring.models.*;
import com.cw.cwSpring.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
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
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Optional<Tender> tender = tenderRepository.findById(TenderId);
        ArrayList<Tender> tenderArray = new ArrayList<>();
        tender.ifPresent(tenderArray::add);
        Iterable<Member> members = memberRepository.findMembersByTenderID(TenderId);
        model.addAttribute("members",members);
        model.addAttribute("tender",tenderArray);
        if(memberRepository.findMembersByTenderIDAndUserID(TenderId,user.getID()).size() == 0) {
            model.addAttribute("offerStatus",true);
        }
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
        if(userRepository.findUserById(user.getID()).getRole().equals("Admin")) {
            model.addAttribute("isAdmin",true);
        }
        return "account";
    }
    @GetMapping("/registration")
    public String registration(Model model) {
        //model.addAttribute("name", "Hello!");
        return "registration";
    }
    @PostMapping("/registration")
    public String registrationForm(@RequestParam String login, @RequestParam String password,@RequestParam String passwordSecond,@RequestParam String name,@RequestParam String phone,@RequestParam String address,Model model) {
        String encodPass = new BCryptPasswordEncoder().encode(password);
        if(!password.equals(passwordSecond)) {
            model.addAttribute("passNotEquals",true);
            return "registration";
        }
        else {
            if(userRepository.findByUsername(login)==null) {
                User user = new User(login,encodPass);
                userRepository.save(user);
                UserData userData = new UserData(user.getId(),phone,address,name);
                userDataRepository.save(userData);
                return "login";
            }
            else {
                model.addAttribute("userExist",true);
                return "registration";
            }
        }
    }
    @PostMapping("/admin")
    public String adminSort(@RequestParam String name, @RequestParam String status,Model model) {
        Iterable<User> users;
        if(!name.equals("")&&!status.equals("none")) {
            if(status.equals("on")) {
                users =userRepository.findUsersByUsernameAndRoleNotNull(name);
            }
            else {
                users =userRepository.findUsersByUsernameAndRoleNull(name);
            }
        }
        else if(!name.equals("")) {
            users = userRepository.findUsersByUsername(name);
        }
        else if(!status.equals("none")) {
            if(status.equals("on")) {
                users =userRepository.findUsersByRoleNotNull();
            }
            else {
                users =userRepository.findUsersByRoleNull();
            }
        }
        else {
            users = userRepository.findAll();
        }
                model.addAttribute("users",users);
                model.addAttribute("userData",userDataRepository.findAll());
                return "adminPage";

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
    @PostMapping("/changeUserPass")
    public String changeUserPass(@RequestParam String oldPass, @RequestParam String newPassFirst,@RequestParam String newPassSecond,Model model) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User dbUser = userRepository.findUserById(user.getID());
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if(encoder.matches(oldPass,dbUser.getPassword())) {
            if(newPassFirst.equals(newPassSecond)){
                String newEncodedPass = new BCryptPasswordEncoder().encode(newPassFirst);
                dbUser.setPassword(newEncodedPass);
                userRepository.save(dbUser);
                return "redirect:/logout";
            }
        }
        return "redirect:/account";
    }
    @GetMapping("/myFinish")
    public String myFinishPage(Model model) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Iterable<Tender> tenders = tenderRepository.findTendersByUserIDAndIsActive(user.getID(),false);
        model.addAttribute("tenders",tenders);
        return "myFinish";
    }
    @PostMapping("/myFinish")
    public String tenderMyFinishSort(@RequestParam String name, @RequestParam String status,@RequestParam Integer priceFrom,@RequestParam Integer priceTo, Model model) {
        TenderSortService tenderSortService = new TenderSortService();
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Iterable<Tender> tender = tenderRepository.findTendersByUserIDAndIsActive(user.getID(),false);
        ArrayList<Tender> tenderArray = new ArrayList<>();
        tender.forEach(tenderArray::add);
        if(!status.equals("none")) {
            if(status.equals("up")) {
                tenderArray = tenderSortService.SortDesc(tenderArray);
            }
            else {
                tenderArray = tenderSortService.SortAsc(tenderArray);
            }
        }
        tenderArray = tenderSortService.SelectInPriceRange(tenderArray,priceFrom,priceTo);
        tenderArray = tenderSortService.FindInArray(tenderArray,name);
        model.addAttribute("tenders",tenderArray);
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
    @GetMapping("/myFinish/startAgain/{TenderId}")
    public String myFinishStartAgain(@PathVariable(value = "TenderId") Integer TenderId, Model model) {
        Tender tender = tenderRepository.findTenderById(TenderId);
        if(tender!=null) {
            tender.setActive(true);
            tenderRepository.save(tender);
        }
        return "redirect:/home";
    }
    @GetMapping("/myOffers")
    public String loadMyOffers(Model model) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("tenders",tenderRepository.getAllTenderByOfferUserID(user.getID()));
        return "myOffers";
    }
    @PostMapping("/myOffers")
    public String tenderMyOffersSort(@RequestParam String name, @RequestParam String status,@RequestParam Integer priceFrom,@RequestParam Integer priceTo, Model model) {
        TenderSortService tenderSortService = new TenderSortService();
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Iterable<Tender> tender = tenderRepository.getAllTenderByOfferUserID(user.getID());
        ArrayList<Tender> tenderArray = new ArrayList<>();
        tender.forEach(tenderArray::add);
        if(!status.equals("none")) {
            if(status.equals("up")) {
                tenderArray = tenderSortService.SortDesc(tenderArray);
            }
            else {
                tenderArray = tenderSortService.SortAsc(tenderArray);
            }
        }
        tenderArray = tenderSortService.SelectInPriceRange(tenderArray,priceFrom,priceTo);
        tenderArray = tenderSortService.FindInArray(tenderArray,name);
        model.addAttribute("tenders",tenderArray);
        return "myOffers";
    }
    @GetMapping("/myTenders")
    public String loadMyTendersPage(Model model) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Iterable<Tender> tenders = tenderRepository.findTendersByUserIDAndIsActive(user.getID(),true);
        model.addAttribute("tenders",tenders);
        return "myTenders";
    }
    @PostMapping("/myTenders")
    public String myTenderSort(@RequestParam String name, @RequestParam String status,@RequestParam Integer priceFrom,@RequestParam Integer priceTo, Model model) {
        TenderSortService tenderSortService = new TenderSortService();
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Iterable<Tender> tender = tenderRepository.findTendersByUserIDAndIsActive(user.getID(),true);
        ArrayList<Tender> tenderArray = new ArrayList<>();
        tender.forEach(tenderArray::add);
        if(!status.equals("none")) {
            if(status.equals("up")) {
                tenderArray = tenderSortService.SortDesc(tenderArray);
            }
            else {
                tenderArray = tenderSortService.SortAsc(tenderArray);
            }
        }
        tenderArray = tenderSortService.SelectInPriceRange(tenderArray,priceFrom,priceTo);
        tenderArray = tenderSortService.FindInArray(tenderArray,name);
        model.addAttribute("tenders",tenderArray);
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
    @GetMapping("/myTenders/selectWinner/{TenderId}")
    public String myTenderFinish(@PathVariable(value = "TenderId") Integer TenderId, Model model) {
        Optional<Tender> tender = tenderRepository.findById(TenderId);
        ArrayList<Tender> tenderArray = new ArrayList<>();
        tender.ifPresent(tenderArray::add);
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
        model.addAttribute("winner",bestMember);
        model.addAttribute("members",members);
        model.addAttribute("tender",tenderArray);
        return "selectWinner";
    }
    @GetMapping("/myTenders/remove/{TenderId}")
    public String removeMyTender(@PathVariable(value = "TenderId") Integer TenderId, Model model) {
        tenderRepository.deleteById(TenderId);
        return "redirect:/myTenders";
    }
    @GetMapping("/home/removeOffer/{TenderId}")
    public String removeMyOffer(@PathVariable(value = "TenderId") Integer TenderId, Model model) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        ArrayList<Member> member = memberRepository.findMembersByTenderIDAndUserID(TenderId,user.getID());
        if(member.size() > 0) {
            memberRepository.delete(member.get(0));
        }
        return "redirect:/home";
    }
    @GetMapping("/admin/changeStatus/{TenderId}")
    public String ChangeUserStatus(@PathVariable(value = "TenderId") Integer TenderId, Model model) {
        User user = userRepository.findUserById(TenderId);
        if(user.getRole() == null) {
            user.setRole("User");
        }
        else {
            if(user.getRole().equals("Admin")) {
            }
            else {
                user.setRole(null);
            }
        }
        userRepository.save(user);
        return "redirect:/admin";
    }
    @GetMapping("/admin/removeUser/{TenderId}")
    public String RemoveUser(@PathVariable(value = "TenderId") Integer TenderId, Model model) {
        User user = userRepository.findUserById(TenderId);
        if(user.getRole()!=null) {
            if(!user.getRole().equals("Admin")) {
                userRepository.delete(user);
            }
        }
        else {
            userRepository.delete(user);
        }
        return "redirect:/admin";
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
    @PostMapping("/myTenders/selectWinner/{TenderId}")
    public String finishByUser(@RequestParam Integer TenderID, @RequestParam Integer winnerID,Model model) {


        Iterable<Member> members = memberRepository.findMembersByTenderID(TenderID);
        Member bestMember = new Member();
        for(Member item : members)
        {
            if(item.getUserID() == winnerID) {
                Tender tender = tenderRepository.findTenderById(TenderID);
                tender.setActive(false);
                tenderRepository.save(tender);

                Winner winner = new Winner();
                winner.setMemberID(item.getId());
                winnerRepository.save(winner);
            }
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
    @GetMapping("/myTenders/finishNone/{TenderId}")
    public String finishMyTenderNone(@PathVariable(value = "TenderId") Integer TenderId, Model model) {
        Tender tender = tenderRepository.findTenderById(TenderId);
        tender.setActive(false);
        tenderRepository.save(tender);
        return "redirect:/myTenders";
    }
}
