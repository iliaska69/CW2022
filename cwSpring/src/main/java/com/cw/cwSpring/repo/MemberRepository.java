package com.cw.cwSpring.repo;

import com.cw.cwSpring.models.Member;
import com.cw.cwSpring.models.Tender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Iterable<Member> findMembersByTenderID(Integer TenderID);
    ArrayList<Member> findMembersByTenderIDAndUserID(Integer TenderID, Integer UserID);

}
