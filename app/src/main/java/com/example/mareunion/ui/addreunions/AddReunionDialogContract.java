package com.example.mareunion.ui.addreunions;

import com.example.mareunion.core.MvpInterface;
import com.example.mareunion.model.Participant;

import java.time.Instant;
import java.util.Set;

public interface AddReunionDialogContract {
    interface Model extends MvpInterface.Model {
        //saves the date of the meeting
        void saveReunionDate(Instant reunionDate);

        //saves the list of participants to the meeting
        void saveInvitedParticipants(Set<Participant> participants);

        //saves the reunion into the service
        void saveReunion(String lieu, String sujet);

        //returns the reunion date
        Instant getReunionDate();

        //returns the list of invited participants
        Set<Participant> getInvitedParticipants();
    }

    /**
     * View interface
     */
    interface View extends MvpInterface.View<Presenter> {
        //show the add reunion dialog
        void showDialog();

        //returns to the meetings
        void returnBackToReunions();

        //updates the meeting date
        void updateReunionDate(Instant reunionDate);

        //updates the list of invited participants
        void updateParticipantsInvitedToTheReunion(String participantsFlattenList);

        //sets an error on the text subject
        void setErrorSujetIsEmpty();

        //sets an error on the text place
        void setErrorLieuIsEmpty();

        //sets an error on the text date
        void setErrorDateIsEmpty();

        //sets an error on the text date if the format is wrong
        void setErrorDateIsInWrongFormat();

        //sets an error on the Participants list if it's empty
        void setErrorParticipantsListIsEmpty();

        //triggers the date picker dialog
        void triggerDatePickerDialog(Instant reunionDate);

        //triggers the time picker dialog
        void triggerTimePickerDialog(Instant reunionDate);

        //triggers the add participants dialog
        void triggerAddParticipantsDialog(Set<Participant> initialParticipants);

    }

    /**
     * Presenter interface
     */
    interface Presenter extends MvpInterface.Presenter {
        //on resumed request updates the participants list and meeting date
        void onResumeRequest();
        //on creating reunion request
        void onCreateReunionRequest(String sujet, String reunionDateTextInput, String lieu);
        //reunion date picker request
        void onReunionDatePickRequest(String reunionDateTextInput);
        //on reunion date has been selected
        void onReunionDateSelected(Instant date);
        //on reunion time has been selected
        void onReunionTimeSelected(Instant mergedDateAndTime);
        //on add participitants has been selected
        void onAddParticipantsRequest();
        //on participants have been changed
        void onParticipantsChanged(Set<Participant> participants);
    }
}
