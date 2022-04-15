package com.cw.cwSpring.repo;

import com.cw.cwSpring.models.Tender;
import com.cw.cwSpring.models.User;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.criteria.CriteriaBuilder;

public interface TenderRepository extends CrudRepository<Tender, Integer> {
    Iterable<Tender> findTendersByUserID(Integer userID);
}