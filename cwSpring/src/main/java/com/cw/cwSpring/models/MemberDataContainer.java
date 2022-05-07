package com.cw.cwSpring.models;

import com.cw.cwSpring.Iterator.Collection;
import com.cw.cwSpring.Iterator.Iterator;

public class MemberDataContainer implements Collection {

    private Member member;
    private String[] rating;

    public MemberDataContainer() {
    }

    public MemberDataContainer(Member member, String[] rating) {
        this.member = member;
        this.rating = rating;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public String getZRating() {
        return rating[0];
    }

    public String[] getRating() {
        return rating;
    }

    public void setRating(String[] rating) {
        this.rating = rating;
    }

    @Override
    public Iterator getIterator() {
        return new RatingIterator();
    }

    private class RatingIterator implements Iterator{

        Integer index = 0;

        @Override
        public boolean hasNext() {
            if(index < rating.length) {
                return true;
            }
            return false;
        }

        @Override
        public Object next() {
            return rating[index++];
        }
    }
}
