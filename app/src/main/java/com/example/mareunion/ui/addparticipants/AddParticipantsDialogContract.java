package com.example.mareunion.ui.addparticipants;

import com.example.mareunion.core.MvpInterface;
import com.example.mareunion.model.Participant;

import java.util.Set;

public interface AddParticipantsDialogContract extends MvpInterface {
    interface Model extends MvpInterface.Model {
        // return the persons added by the user
        Set<Participant> getParticipantsSet();

        // add a person to the list of invited persons to the meeting
        void addParticipant(Participant participant);

        // the last saved persons list will be the last one, so we can notify the caller
        void commit();

        // set the initial persons in the set/list
        void setInitialParticipants(Set<Participant> initialParticipants);

        // allow the model to notify a caller, that the persons set has changed for the last time
        interface OnParticipantsSetFinalChangedListener {
            void onParticipantsListFinalChanged(Set<Participant> participantList);
        }

        // set the listener to notify the caller, that the persons set has changed for the last time
        void setOnParticipantsSetFinalChangedListener(
                OnParticipantsSetFinalChangedListener onParticipantsSetChangedListener
        );
    }

    /**
     * View interface
     */
    interface View extends MvpInterface.View<Presenter> {
        // show the add persons dialog to the screen
        void showDialog();
        // update the ui person set
        void updateParticipantsList(Set<Participant> participantsSet);
    }

    /**
     * Presenter interface
     */
    interface Presenter extends MvpInterface.Presenter {
        // method called from the view, when the user click to add a person to the meeting
        void onParticipantAdded(Participant person);
        // ask explicitly the presenter to refresh the persons set from the model after view load
        void onViewLoaded();
        // method called from the view, when the user click to validate the persons list
        void commit();
    }
}
