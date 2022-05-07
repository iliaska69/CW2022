package com.cw.cwSpring.Single;

import com.cw.cwSpring.Services.Implementation.DataLoggerServiceImp;
import org.springframework.beans.factory.annotation.Autowired;

public class DataLogger {
    private static DataLogger dataLogger;

    @Autowired
    DataLoggerServiceImp dataLoggerServiceImp;

    public static DataLogger getInstance() {
        if(dataLogger == null) {
            dataLogger = new DataLogger();
        }
        return dataLogger;
    }
    private DataLogger() {
    }

    public void LogData(Integer userId,String action) {
        dataLoggerServiceImp.LogData(userId,action);
    }
}
