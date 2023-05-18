package com.example.mareunion.ui.addreunions;

import com.example.mareunion.core.MvpInterface;
import com.example.mareunion.model.Participant;

import java.time.Instant;
import java.util.Set;

public interface AddReunionDialogContract {
    interface Model extends MvpInterface.Model {
        void saveReunionDate(Instant reunionDate);

        void saveInvitedParticipants(Set<Participant> participants);

        void saveReunion(String lieu, String sujet);

        Instant getReunionDate();

        Set<Participant> getInvitedParticipants();
    }

    /**
     * View interface
     */
    interface View extends MvpInterface.View<Presenter> {

        void showDialog();

        void returnBackToReunions();

        void updateReunionDate(Instant reunionDate);

        void updateParticipantsInvitedToTheReunion(String participantsFlattenList);

        void setErrorSujetIsEmpty();

        void setErrorLieuIsEmpty();

        void setErrorDateIsEmpty();

        void setErrorDateIsInWrongFormat();

        void triggerDatePickerDialog(Instant reunionDate);

        void triggerTimePickerDialog(Instant reunionDate);

        void triggerAddParticipantsDialog(Set<Participant> initialParticipants);

    }

    /**
     * Presenter interface
     */
    interface Presenter extends MvpInterface.Presenter {

        void onResumeRequest();

        void onCreateReunionRequest(String sujet, String reunionDateTextInput, String lieu);

        void onReunionDatePickRequest(String reunionDateTextInput);

        void onReunionDateSelected(Instant date);

        void onReunionTimeSelected(Instant mergedDateAndTime);

        void onAddParticipantsRequest();

        void onParticipantsChanged(Set<Participant> participants);
    }
}
