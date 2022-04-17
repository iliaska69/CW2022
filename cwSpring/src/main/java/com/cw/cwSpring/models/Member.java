package com.cw.cwSpring.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer userID;
    private Integer tenderID;
    private String offerDescription;
    private Integer offerPrice;
    private Integer term;

    public Member(Integer userID, Integer tenderID, String offerDescription, Integer offerPrice, Integer term) {
        this.userID = userID;
        this.tenderID = tenderID;
        this.offerDescription = offerDescription;
        this.offerPrice = offerPrice;
        this.term = term;
    }

    public Member(){}


    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }


    public String getOfferDescription() {
        return offerDescription;
    }

    public void setOfferDescription(String offerDescription) {
        this.offerDescription = offerDescription;
    }

    public Integer getOfferPrice() {
        return offerPrice;
    }

    public void setOfferPrice(Integer offerPrice) {
        this.offerPrice = offerPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getTenderID() {
        return tenderID;
    }

    public void setTenderID(Integer tenderID) {
        this.tenderID = tenderID;
    }
}
