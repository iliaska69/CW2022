package com.cw.cwSpring.repo;

import com.cw.cwSpring.models.Member;
import com.cw.cwSpring.models.Tender;
import org.springframework.data.repository.CrudRepository;

public interface MemberRepository extends CrudRepository<Member, Integer> {
    Iterable<Member> findMembersByTenderID(Integer TenderID);
}
