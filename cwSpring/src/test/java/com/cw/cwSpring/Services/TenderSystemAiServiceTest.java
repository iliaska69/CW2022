package com.cw.cwSpring.Services;

import com.cw.cwSpring.models.Tender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TenderSystemAiServiceTest {

    TenderSystemAiService tenderSystemAiService;
    ArrayList<Tender> tenderArray;
    @BeforeEach
    void setUp() {
        tenderSystemAiService = new TenderSystemAiService();
        tenderArray = new ArrayList<>();
        tenderArray.add(new Tender("name","description",10,100,10));
        tenderArray.add(new Tender("name-","description",10,200,200));
        tenderArray.add(new Tender("name","description",10,50,5));
        tenderArray.add(new Tender("name","description",5,5,5));
    }

    @Test
    void findBestTenderByPrice() {
        assertEquals(5, tenderSystemAiService.FindBestTenderByPrice(tenderArray).getPrice());
    }

    @Test
    void findBestTenderByTerm() {
        assertEquals(5, tenderSystemAiService.FindBestTenderByTerm(tenderArray).getPrice());
    }

    @Test
    void findBestTenderByPriceAndTerm() {
        assertEquals(5, tenderSystemAiService.FindBestTenderByPriceAndTerm(tenderArray).getPrice());
    }

    @Test
    void calculateCf() {
        assertEquals(10, tenderSystemAiService.CalculateCf(100,10));
    }
}