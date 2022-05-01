package com.cw.cwSpring.repo;

import com.cw.cwSpring.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
    User findUserById(Integer id);
    Iterable<User> findUsersByUsername(String name);
    Iterable<User> findUsersByRoleNotNull();
    Iterable<User> findUsersByRoleNull();
    Iterable<User> findUsersByUsernameAndRoleNotNull(String name);
    Iterable<User> findUsersByUsernameAndRoleNull(String name);
}
