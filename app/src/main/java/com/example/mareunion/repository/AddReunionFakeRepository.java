package com.example.mareunion.repository;

import com.example.mareunion.di.DI;
import com.example.mareunion.model.Lieu;
import com.example.mareunion.model.Participant;
import com.example.mareunion.model.Reunion;
import com.example.mareunion.service.ReunionApiService;
import com.example.mareunion.ui.addreunions.AddReunionDialogContract;
import com.example.mareunion.ui.utils.DateEasy;

import java.time.Instant;
import java.util.Set;
import java.util.TreeSet;

public class AddReunionFakeRepository implements AddReunionDialogContract.Model {
    private Instant mReunionDate = DateEasy.now();

    private Set<Participant> mPersonsInvitedToTheReunion = new TreeSet<>();

    private final ReunionApiService mApiService = DI.getReunionApiService();


    @Override
    public void saveReunionDate(Instant reunionDate) {
        mReunionDate = reunionDate;
    }

    @Override
    public void saveInvitedParticipants(Set<Participant> participants) {
        mPersonsInvitedToTheReunion = participants;
    }

    @Override
    public void saveReunion(String lieu, String sujet) {
        Reunion reunion = new Reunion(mReunionDate, sujet, new Lieu(lieu));

        for (Participant participant : mPersonsInvitedToTheReunion){
            reunion.addParticipants(participant);
        }
        mApiService.addReunion(reunion);

    }

    @Override
    public Instant getReunionDate() {
        return mReunionDate;
    }

    @Override
    public Set<Participant> getInvitedParticipants() {
        return mPersonsInvitedToTheReunion;
    }
}
