package com.example.mareunion.service;

import com.example.mareunion.model.Reunion;

import java.util.ArrayList;
import java.util.List;

public class DummyReunionApiService implements ReunionApiService{

    List<Reunion> reunions= new ArrayList<>();

    @Override
    public List<Reunion> getReunions() {
        return reunions;
    }

    @Override
    public void deleteReunion(Reunion reunion) {
        reunions.remove(reunion);
    }

    @Override
    public void addReunion(Reunion reunion) {
        reunions.add(reunion);
    }
}
