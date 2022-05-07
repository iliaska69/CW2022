package com.cw.cwSpring.models;

import com.cw.cwSpring.models.Base.EntityBase;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Winner extends EntityBase {
    private Integer userID;
    private Integer memberID;

    public Winner() {
    }


    public Winner(Integer userID, Integer memberID) {
        this.userID = userID;
        this.memberID = memberID;
    }


    public Integer getUserID() {
        return userID;
    }

    public void setUserID(Integer userID) {
        this.userID = userID;
    }

    public Integer getMemberID() {
        return memberID;
    }

    public void setMemberID(Integer memberID) {
        this.memberID = memberID;
    }
}
