package com.example.mareunion.ui.utils;

import com.example.mareunion.model.Participant;
import com.google.common.base.Joiner;

import java.util.Set;
import java.util.TreeSet;

public class ParticipantsListFormatter {
    /**
     * The persons list
     */
    private final Set<Participant> mParticipantsSet;

    /**
     * Constructor
     * @param participantsSet the persons list
     */
    public ParticipantsListFormatter(Set<Participant> participantsSet) {
        mParticipantsSet = participantsSet;
    }

    /**
     * Format the persons list
     * @return the formatted persons list as string
     */
    public String format(){
        // build the email list from the Person domain model objects
        Set<String> emailsSet = new TreeSet<>();
        // for each person, add the email to the set
        for(Participant participant : mParticipantsSet){
            // add the email
            emailsSet.add(participant.getMail());
        }
        // build the string from the string set
        return "Persons invited list:\n\n" + Joiner.on("\n").join(emailsSet);
    }
}
