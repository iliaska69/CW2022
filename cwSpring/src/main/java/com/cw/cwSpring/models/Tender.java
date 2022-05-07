package com.cw.cwSpring.models;

import com.cw.cwSpring.models.Base.EntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Tender extends EntityBase {
    private Integer userID;
    private String name;
    private String description;
    private Integer term;
    private Integer price;
    private Boolean isActive;

    public Tender() {
    }

    public Tender(String name, String description,Integer term,Integer price, Integer userID) {
        this.name = name;
        this.userID = userID;
        this.description = description;
        this.term = term;
        this.price = price;
        this.isActive = true;
    }

    public boolean isActive() {return isActive;}

    public void setActive(boolean active) {isActive = active;}

    public Integer getUserID() {return userID;}

    public void setUserID(Integer userID) {this.userID = userID;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getTerm() {
        return term;
    }

    public void setTerm(Integer term) {
        this.term = term;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
