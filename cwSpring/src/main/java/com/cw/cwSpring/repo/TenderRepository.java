package com.cw.cwSpring.repo;

import com.cw.cwSpring.models.Tender;
import com.cw.cwSpring.models.User;
import org.springframework.data.repository.CrudRepository;

public interface TenderRepository extends CrudRepository<Tender, Integer> {

}