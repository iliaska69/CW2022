package com.cw.cwSpring.repo;

import com.cw.cwSpring.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
    User findUserById(Integer id);
    void removeUserById(Integer id);
}
