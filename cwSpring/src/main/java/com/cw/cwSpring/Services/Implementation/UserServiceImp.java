package com.cw.cwSpring.Services.Implementation;

import com.cw.cwSpring.Services.Interfaces.UserService;
import com.cw.cwSpring.models.User;
import com.cw.cwSpring.repo.UserDataRepository;
import com.cw.cwSpring.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImp implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public Iterable<User> GetAllUsers() {
        return userRepository.findAll();
    }
}
