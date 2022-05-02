package com.cw.cwSpring.Services;

import com.cw.cwSpring.models.Tender;

import java.util.ArrayList;

public class TenderSystemAiService {
    Tender FindBestTenderByPrice(ArrayList<Tender> array){
        Integer bestID=0;
        for(Integer i =0;i<array.size();i++) {
            if(array.get(bestID).getPrice()>array.get(i).getPrice()) {
                bestID = i;
            }
        }
        return array.get(bestID);
    }
    Tender FindBestTenderByTerm(ArrayList<Tender> array){
        Integer bestID=0;
        for(Integer i =0;i<array.size();i++) {
            if(array.get(bestID).getTerm()>array.get(i).getTerm()) {
                bestID = i;
            }
        }
        return array.get(bestID);
    }
    Tender FindBestTenderByPriceAndTerm(ArrayList<Tender> array){
        Integer bestID=0;
        for(Integer i =0;i<array.size();i++) {
            if(CalculateCf(array.get(bestID).getPrice(),array.get(bestID).getTerm())>CalculateCf(array.get(i).getPrice(),array.get(i).getTerm())) {
                bestID = i;
            }
        }
        return array.get(bestID);
    }
    Integer CalculateCf(Integer price,Integer term) {
        return price/term;
    }
}
