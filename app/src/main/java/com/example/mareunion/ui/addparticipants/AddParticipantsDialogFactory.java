package com.example.mareunion.ui.addparticipants;

import com.example.mareunion.model.Participant;
import com.example.mareunion.repository.AddParticipantsFakeRepository;

import java.util.Set;

public class AddParticipantsDialogFactory {

    public AddParticipantsDialogFragment getFragment(

            Set<Participant> participants,
            AddParticipantsDialogContract.Model.OnParticipantsSetFinalChangedListener onParticipantsChangedListener)
    {
        AddParticipantsDialogContract.Model model = new AddParticipantsFakeRepository();
        model.setInitialParticipants(participants);
        model.setOnParticipantsSetFinalChangedListener(onParticipantsChangedListener::onParticipantsListFinalChanged);

        AddParticipantsDialogFragment fragment = AddParticipantsDialogFragment.newInstance();
        new AddParticipantsDialogPresenter(fragment, model);

        return fragment;
    }
}
