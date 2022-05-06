package com.cw.cwSpring.Services.Implementation;

import com.cw.cwSpring.Services.Interfaces.UserDataService;
import com.cw.cwSpring.models.UserData;
import com.cw.cwSpring.repo.UserDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDataServiceImp implements UserDataService {

    @Autowired
    UserDataRepository userDataRepository;

    @Override
    public Iterable<UserData> GetAllUserData() {
        return userDataRepository.findAll();
    }
}
