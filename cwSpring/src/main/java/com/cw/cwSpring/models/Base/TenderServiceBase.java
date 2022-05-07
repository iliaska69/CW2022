package com.cw.cwSpring.models.Base;

import com.cw.cwSpring.models.Tender;

import java.util.ArrayList;

public abstract class TenderServiceBase {

    public boolean isWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(boolean workStatus) {
        this.workStatus = workStatus;
    }

    private boolean workStatus;

    public abstract void DoMainOperation(ArrayList<Tender> array, String fieldSearch);

}
