package com.cw.cwSpring.repo;

import com.cw.cwSpring.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Integer> {

}
