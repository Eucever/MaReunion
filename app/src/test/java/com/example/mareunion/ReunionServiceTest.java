package com.example.mareunion;

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

import java.util.Arrays;
import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class ReunionServiceTest{

    private ReunionApiService service;

    @Before
    public void setup(){
        service = DI.getNewInstanceApiService();
    }

    @Test
    public void addReunionWithSucess(){
        List<Participant>participants = Arrays.asList(new Participant("partic1"),new Participant("partic2"));
        Reunion reunionToAdd = new Reunion("Reunion 1","15h00","Sujet1",new Lieu("Paris"), participants);
        service.addReunion(reunionToAdd);
        assertTrue(service.getReunions().contains(reunionToAdd));
    }

}