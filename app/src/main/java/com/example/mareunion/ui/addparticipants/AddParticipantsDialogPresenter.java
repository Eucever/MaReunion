package com.example.mareunion.ui.addparticipants;

import com.example.mareunion.model.Participant;

public class AddParticipantsDialogPresenter implements AddParticipantsDialogContract.Presenter {
    private final AddParticipantsDialogContract.View mView;

    /**
     * The model
     */
    private final AddParticipantsDialogContract.Model mModel;

    /**
     * Constructor for the presenter
     * @param view the view
     * @param model the model
     */
    public AddParticipantsDialogPresenter(AddParticipantsDialogContract.View view, AddParticipantsDialogContract.Model model) {
        mView = view;
        mModel = model;
        // important : immediately attach the presenter to the view
        mView.attachPresenter(this);
    }

    /**
     * Add a person to the list (set) of persons
     * @param participant the person to add
     */

    @Override
    public void onParticipantAdded(Participant participant) {
        mModel.addParticipant(participant);
        mView.updateParticipantsList(mModel.getParticipantsSet());
    }

    /**
     * Ask explicitly the presenter to refresh the persons set from the model
     */
    @Override
    public void onViewLoaded() {
        mView.updateParticipantsList(mModel.getParticipantsSet());
    }

    /**
     * Tell the model that's the final list of persons that is currently set
     * So we can notify the listener
     */
    @Override
    public void commit() {
        mModel.commit();
    }

    /**
     * Init the view, and show it!
     */
    @Override
    public void init() {
        mView.showDialog();
    }
}
