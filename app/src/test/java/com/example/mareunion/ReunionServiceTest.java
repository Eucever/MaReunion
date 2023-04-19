package com.example.mareunion;

import org.hamcrest.collection.IsIterableContainingInAnyOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

import com.example.mareunion.di.DI;
import com.example.mareunion.model.Lieu;
import com.example.mareunion.model.Participant;
import com.example.mareunion.model.Reunion;
import com.example.mareunion.service.ReunionApiService;
import com.example.mareunion.utils.DateEasy;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class ReunionServiceTest{

    private ReunionApiService service;
    private Reunion reunion1;
    private Reunion reunion2;
    private Reunion reunion3;

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
    @Test
    public void getReunionEmptyAtStart(){
        List<Reunion> mReunions = service.getReunions();
        assertTrue(mReunions.isEmpty());
    }
    @Test
    public void addReunionsWithSuccess(){
        service.addReunion(reunion1);
        service.addReunion(reunion2);
        service.addReunion(reunion3);

        assertTrue(service.getReunions().contains(reunion1));
        assertTrue(service.getReunions().contains(reunion2));
        assertTrue(service.getReunions().contains(reunion3));

        assertEquals(service.getReunions().get(0), reunion1);
        assertEquals(service.getReunions().get(1), reunion2);
        assertEquals(service.getReunions().get(2), reunion3);
    }

    @Test
    public void deleteReunionsWithSuccess(){
        service.addReunion(reunion1);
        service.addReunion(reunion2);

        service.deleteReunion(reunion2);
        assertFalse(service.getReunions().contains(reunion2));

        service.deleteReunion(reunion1);
        assertTrue(service.getReunions().isEmpty());
    }


}