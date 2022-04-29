package com.cw.cwSpring.repo;

import com.cw.cwSpring.models.Member;
import com.cw.cwSpring.models.Tender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    Iterable<Member> findMembersByTenderID(Integer TenderID);
    ArrayList<Member> findMembersByTenderIDAndUserID(Integer TenderID, Integer UserID);
    @Query(value = "SELECT m FROM Member m WHERE m.id IN (SELECT w.memberID FROM Winner w WHERE w.memberID IN (SELECT m.id FROM Member m WHERE m.tenderID =?1))")
    Member getWinnerByTenderID(Integer id);
}
