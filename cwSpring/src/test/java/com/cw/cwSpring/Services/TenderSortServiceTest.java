package com.cw.cwSpring.Services;

import com.cw.cwSpring.models.Tender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TenderSortServiceTest {

    TenderSortService tenderSortService;
    ArrayList<Tender> tenderArray;
    @Test
    void sortAsc() {
        assertEquals(5, tenderSortService.SortAsc(tenderArray).get(0).getPrice());
    }

    @Test
    void swap() {
        assertEquals(200, tenderSortService.Swap(tenderArray,0,1).get(0).getPrice());
    }

    @Test
    void sortDesc() {
        assertEquals(200, tenderSortService.SortDesc(tenderArray).get(0).getPrice());
    }

    @Test
    void findInArray() {
        assertEquals(2, tenderSortService.FindInArray(tenderArray,"name").size());
    }

    @Test
    void selectInPriceRange() {
        assertEquals(2, tenderSortService.SelectInPriceRange(tenderArray,5,10).size());
    }
    @Test
    void selectInPriceRangeSecond() {
        assertEquals(2, tenderSortService.SelectInPriceRange(tenderArray,10,0).size());
    }
    @Test
    void selectInPriceRangeAnother() {
        assertEquals(2, tenderSortService.SelectInPriceRange(tenderArray,0,10).size());
    }

    @BeforeEach
    void setUp() {
        tenderSortService = new TenderSortService();
        tenderArray = new ArrayList<>();
        tenderArray.add(new Tender("name","description",10,10,10));
        tenderArray.add(new Tender("name-","description",200,200,200));
        tenderArray.add(new Tender("name","description",5,5,5));
    }
}