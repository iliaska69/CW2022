package com.cw.cwSpring.repo;

import com.cw.cwSpring.models.Tender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TenderRepository extends JpaRepository<Tender, Integer> {
    Iterable<Tender> findTendersByUserID(Integer userID);
    Tender findTenderById(Integer tenderID);
    Iterable<Tender> findTendersByIsActive(Boolean isActive);
    Iterable<Tender> findTendersByUserIDAndIsActive(Integer userID, Boolean isActive);
    //@Query(value = "SELECT Tender FROM Tender WHERE Tender.id =? 1")
    //Iterable<Tender> getIdCountryByName(Integer name);
}