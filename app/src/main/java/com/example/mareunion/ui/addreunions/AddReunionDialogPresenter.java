package com.example.mareunion.ui.addreunions;

import androidx.annotation.NonNull;

import com.example.mareunion.model.Participant;
import com.example.mareunion.ui.utils.DateEasy;
import com.example.mareunion.ui.utils.ParticipantsListFormatter;

import java.time.Instant;
import java.util.Set;

public class AddReunionDialogPresenter implements AddReunionDialogContract.Presenter {

    private final AddReunionDialogContract.View mView;

    private final AddReunionDialogContract.Model mModel;

    public AddReunionDialogPresenter(
            @NonNull AddReunionDialogContract.View view,
            @NonNull AddReunionDialogContract.Model model) {
        mView = view;
        mModel = model;
        mView.attachPresenter(this);
    }


    @Override
    public void init() {
        mView.showDialog();
    }

    @Override
    public void onResumeRequest() {
        onParticipantsChanged(mModel.getInvitedParticipants());

        mView.updateReunionDate(mModel.getReunionDate());
    }

    @Override
    public void onCreateReunionRequest(String sujet, String reunionDateTextInput, String lieu) {
        Instant reunionDate = null;

        boolean isError = false;

        if(lieu.isEmpty()){
            isError = true;
            mView.setErrorLieuIsEmpty();
        }

        if (sujet.isEmpty()){
            mView.setErrorSujetIsEmpty();
        }

        if (reunionDateTextInput.isEmpty()){
            isError = true;
            mView.setErrorDateIsEmpty();
        } else if ((reunionDate = DateEasy.parseDateTimeStringToInstant(reunionDateTextInput)) == null){
            isError = true;
            mView.setErrorDateIsInWrongFormat();
        }

        if(!isError){
            mModel.saveReunionDate(reunionDate);

            mModel.saveReunion(lieu, sujet);

            mView.returnBackToReunions();
        }

    }

    @Override
    public void onReunionDatePickRequest(String reunionDateTextInput) {
        mModel.saveReunionDate(DateEasy.parseDateTimeOrDateOrReturnNow(reunionDateTextInput));

        mView.updateReunionDate(mModel.getReunionDate());

        mView.triggerDatePickerDialog(mModel.getReunionDate());


    }

    @Override
    public void onReunionDateSelected(Instant date) {
        mModel.saveReunionDate(date);

        mView.updateReunionDate(mModel.getReunionDate());

        mView.triggerTimePickerDialog(mModel.getReunionDate());
    }

    @Override
    public void onReunionTimeSelected(Instant mergedDateAndTime) {
        mModel.saveReunionDate(mergedDateAndTime);

        mView.updateReunionDate(mModel.getReunionDate());

    }

    @Override
    public void onAddParticipantsRequest() {
        mView.triggerAddParticipantsDialog(mModel.getInvitedParticipants());

    }

    @Override
    public void onParticipantsChanged(Set<Participant> participants) {
        mModel.saveInvitedParticipants(participants);

        ParticipantsListFormatter participantsListFormatter = new ParticipantsListFormatter(participants);

        mView.updateParticipantsInvitedToTheReunion(participantsListFormatter.format());

    }
}
