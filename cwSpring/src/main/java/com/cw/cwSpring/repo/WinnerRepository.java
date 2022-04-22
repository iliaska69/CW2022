package com.cw.cwSpring.repo;

import com.cw.cwSpring.models.Member;
import com.cw.cwSpring.models.Winner;
import org.springframework.data.jpa.repository.JpaRepository;


public interface WinnerRepository extends JpaRepository<Winner, Integer> {

}
