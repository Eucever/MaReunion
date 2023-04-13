package com.example.mareunion.service;

import com.example.mareunion.model.Reunion;

import java.util.List;

public interface ReunionApiService {

    List<Reunion> getReunions();

    void addReunion(Reunion reunion);

    void deleteReunion(Reunion reunion);

}
