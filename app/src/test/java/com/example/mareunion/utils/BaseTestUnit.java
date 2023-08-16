package com.example.mareunion.utils;

import com.example.mareunion.di.DI;
import com.example.mareunion.model.Lieu;
import com.example.mareunion.model.Participant;
import com.example.mareunion.model.Reunion;
import com.example.mareunion.service.ReunionApiService;
import com.example.mareunion.ui.utils.DateEasy;

import org.junit.Before;

import java.time.Instant;

public class BaseTestUnit {
    protected ReunionApiService service;
    protected Reunion reunion1;
    protected Reunion reunion2;
    protected Reunion reunion3;

    @Before
    public void setup(){
        service = DI.getNewInstanceApiService();

        Participant participant1 = new Participant("partcip1@gmail.com");
        Participant participant2 = new Participant("partcip2@outlook.fr");
        Participant participant3 = new Participant("partcip3@laposte.net");
        Participant participant4 = new Participant("partcip4@gmail.com");


        Lieu lieu1 = new Lieu("Salle A1");
        Lieu lieu2 = new Lieu("Salle A2");
        Lieu lieu3 = new Lieu("Salle B1");

        Instant now = DateEasy.now();
        Instant date1 = DateEasy.plusDays(now, 10);
        Instant date2 = DateEasy.plusDays(now, 11);
        Instant date3 = DateEasy.plusDays(now, 15);

        reunion1 = new Reunion(date1,"Reunion Infos",lieu1,participant1,participant3,participant4);
        reunion2 = new Reunion(date2,"Conference",lieu2,participant3,participant1,participant4);
        reunion3 = new Reunion(date3,"2eme conference",lieu3,participant1,participant2,participant3,participant4);


    }
}
