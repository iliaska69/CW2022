package com.cw.cwSpring.Services.Implementation;

import com.cw.cwSpring.Services.Interfaces.AdminService;
import com.cw.cwSpring.Services.Interfaces.TenderService;
import com.cw.cwSpring.Services.TenderSortService;
import com.cw.cwSpring.models.Tender;
import com.cw.cwSpring.models.User;
import com.cw.cwSpring.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AdminServiceImp implements AdminService {

    @Autowired
    UserRepository userRepository;

    @Override
    public void RemoveUserBuId(Integer TenderId) {
        User user = userRepository.findUserById(TenderId);
        TenderSortService tenderSortService = new TenderSortService();
        tenderSortService.DoMainOperation(new ArrayList<Tender>(),"NONE");
        if(user.getRole()!=null) {
            if(!user.getRole().equals("Admin")) {
                userRepository.delete(user);
            }
        }
        else {
            userRepository.delete(user);
        }
    }
}
