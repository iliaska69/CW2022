package com.cw.cwSpring.Services.Interfaces;

import com.cw.cwSpring.models.Tender;

public interface TenderService {
    public Iterable<Tender> GetAllActiveTenders();
    public void SaveTender(Tender tender);
    public Iterable<Tender> GetAllActiveTendersUsingSort(String name, String status,Integer priceFrom, Integer priceTo);
}
