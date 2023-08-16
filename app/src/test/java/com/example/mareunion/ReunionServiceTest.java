package com.example.mareunion;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

import com.example.mareunion.model.Reunion;
import com.example.mareunion.utils.BaseTestUnit;

import java.util.List;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(JUnit4.class)
public class ReunionServiceTest extends BaseTestUnit {

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