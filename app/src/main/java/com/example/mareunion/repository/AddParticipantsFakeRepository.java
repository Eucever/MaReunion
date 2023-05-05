package com.example.mareunion.repository;

import com.example.mareunion.model.Participant;
import com.example.mareunion.ui.addparticipants.AddParticipantsDialogContract;


import java.util.Set;
import java.util.TreeSet;

/**
 * Model/Repository for the MVP AddPersonsDialog
 * Note that, in a real app, this content MUST be dispatched to DAO, models, services, etc.
 */
public class AddParticipantsFakeRepository implements AddParticipantsDialogContract.Model {

    /**
     * The list of (set of unique) persons
     * NEVER ACCESS THIS LIST DIRECTLY, IN A REAL APP, USE A DAO
     */
    private final Set<Participant> mParticipantsSet = new TreeSet<>();

    /**
     * The listener to notify when the list of persons is updated
     */
    private OnParticipantsSetFinalChangedListener mOnParticipantsSetFinalChangedListener;

    /**
     * Get the list (set) of persons
     * @return the list (set) of persons
     */
    @Override
    public Set<Participant> getParticipantsSet() {
        return mParticipantsSet;
    }

    /**
     * Add a person to the list (set) of persons
     * @param participant the person to add
     */
    @Override
    public void addParticipant(Participant participant) {
        mParticipantsSet.add(participant);
    }

    /**
     * Tell the model that's the final list of persons that is currently set
     * So we can notify the listener
     */
    @Override
    public void commit() {
        if (mOnParticipantsSetFinalChangedListener != null) {
            mOnParticipantsSetFinalChangedListener.onParticipantsListFinalChanged(mParticipantsSet);
        }
    }

    @Override
    public void setInitialParticipants(Set<Participant> initialParticipants) {
        mParticipantsSet.clear();
        mParticipantsSet.addAll(initialParticipants);
    }

    /**
     * Set the listener to notify when the list of persons is updated
     * @param onParticipantsSetFinalChangedListener the listener
     */
    public void setOnParticipantsSetFinalChangedListener(
            OnParticipantsSetFinalChangedListener onParticipantsSetFinalChangedListener) {
        mOnParticipantsSetFinalChangedListener = onParticipantsSetFinalChangedListener;
    }
}