package com.cw.cwSpring.repo;

import com.cw.cwSpring.models.UserData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataRepository extends JpaRepository<UserData, Integer> {
    UserData findUserDataByUserID(Integer UserID);
}
