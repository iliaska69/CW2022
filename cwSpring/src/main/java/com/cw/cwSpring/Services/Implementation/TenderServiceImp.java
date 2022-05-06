package com.cw.cwSpring.Services.Implementation;

import com.cw.cwSpring.Services.CustomUserDetails;
import com.cw.cwSpring.Services.Interfaces.TenderService;
import com.cw.cwSpring.Services.TenderSortService;
import com.cw.cwSpring.models.Tender;
import com.cw.cwSpring.repo.TenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class TenderServiceImp implements TenderService {

    @Autowired
    TenderRepository tenderRepository;

    @Override
    public Iterable<Tender> GetAllActiveTenders() {
        return tenderRepository.findTendersByIsActive(true);
    }

    @Override
    public void SaveTender(Tender tender) {
        CustomUserDetails user = (CustomUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        tender.setUserID(user.getID());
        tenderRepository.save(tender);
    }

    @Override
    public Iterable<Tender> GetAllActiveTendersUsingSort(String name, String status, Integer priceFrom, Integer priceTo) {
        TenderSortService tenderSortService = new TenderSortService();
        Iterable<Tender> tender = tenderRepository.findTendersByIsActive(true);
        ArrayList<Tender> tenderArray = new ArrayList<>();
        tender.forEach(tenderArray::add);
        if(!status.equals("none")) {
            if(status.equals("up")) {
                tenderArray = tenderSortService.SortDesc(tenderArray);
            }
            else {
                tenderArray = tenderSortService.SortAsc(tenderArray);
            }
        }
        tenderArray = tenderSortService.SelectInPriceRange(tenderArray,priceFrom,priceTo);
        tenderArray = tenderSortService.FindInArray(tenderArray,name);
        return tenderArray;
    }
}
