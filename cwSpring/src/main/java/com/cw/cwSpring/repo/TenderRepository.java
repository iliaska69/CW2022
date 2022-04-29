package com.cw.cwSpring.repo;

import com.cw.cwSpring.models.Tender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TenderRepository extends JpaRepository<Tender, Integer> {
    Iterable<Tender> findTendersByUserID(Integer userID);
    Tender findTenderById(Integer tenderID);
    Iterable<Tender> findTendersByIsActive(Boolean isActive);
    Iterable<Tender> findTendersByUserIDAndIsActive(Integer userID, Boolean isActive);
    @Query(value = "SELECT t FROM Tender t WHERE t.id =?1")
    Tender getIdCountryByName(Integer id);
    @Query(value = "SELECT t FROM Tender t WHERE t.id IN (SELECT m.tenderID FROM Member m WHERE m.userID =?1) AND t.isActive = true")
    Iterable<Tender> getAllTenderByOfferUserID(Integer Userid);
}