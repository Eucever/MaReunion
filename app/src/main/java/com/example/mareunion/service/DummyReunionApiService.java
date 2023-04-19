package com.example.mareunion.service;

import com.example.mareunion.model.Reunion;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

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
        if(!reunions.contains(reunion)) {
            reunions.add(reunion);
        }
    }
}
