package com.cw.cwSpring.Services;

import com.cw.cwSpring.models.Tender;

import java.util.ArrayList;

public class TenderSortService {
    public ArrayList<Tender> SortAsc(ArrayList<Tender> array) {
        boolean needIteration = true;
        while (needIteration) {
            needIteration = false;
            for (int i = 1; i < array.size(); i++) {
                if (array.get(i).getPrice() < array.get(i-1).getPrice()) {
                    Tender buffer = array.get(i);
                    array.set(i,array.get(i-1));
                    array.set(i-1,buffer);
                    needIteration = true;
                }
            }
        }
        return array;
    }
    public ArrayList<Tender> SortDesc(ArrayList<Tender> array) {
        boolean needIteration = true;
        while (needIteration) {
            needIteration = false;
            for (int i = 1; i < array.size(); i++) {
                if (array.get(i).getPrice() > array.get(i-1).getPrice()) {
                    Tender buffer = array.get(i);
                    array.set(i,array.get(i-1));
                    array.set(i-1,buffer);
                    needIteration = true;
                }
            }
        }
        return array;
    }
    public ArrayList<Tender> FindInArray(ArrayList<Tender> array, String fieldSearch) {
        if(!fieldSearch.equals("")){
            ArrayList<Tender> newTenders = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                if(array.get(i).getName().equals(fieldSearch)) {
                    newTenders.add(array.get(i));
                }
            }
            return  newTenders;
        }
        else {
            return array;
        }
    }
    public ArrayList<Tender> SelectInPriceRange(ArrayList<Tender> array,Integer from, Integer to) {
        if(from==0 && to==0) {
            return array;
        }
        else {
            ArrayList<Tender> newTenders = new ArrayList<>();
            for (int i = 0; i < array.size(); i++) {
                if(from== 0) {
                    if(array.get(i).getPrice() <= to) {
                        newTenders.add(array.get(i));
                    }
                }
                else if(to== 0) {
                    if(array.get(i).getPrice() >= from) {
                        newTenders.add(array.get(i));
                    }
                }
                else {
                    if(array.get(i).getPrice() >= from &&array.get(i).getPrice() <= to) {
                        newTenders.add(array.get(i));
                    }
                }
            }
            return  newTenders;
        }
    }
}
