package com.example.mareunion.di;

import com.example.mareunion.service.DummyReunionApiService;
import com.example.mareunion.service.ReunionApiService;

public class DI {
    private static ReunionApiService service = new DummyReunionApiService();

    public static ReunionApiService getReunionApiService() {return service;}

    public static ReunionApiService getNewInstanceApiService() {
        return new DummyReunionApiService();
    }
}
