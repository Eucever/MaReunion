package com.example.mareunion.ui.addreunions;

import com.example.mareunion.repository.AddReunionFakeRepository;

public class AddReunionDialogFactory {

    public AddReunionDialogFragment getFragment(){
        AddReunionDialogContract.Model model = new AddReunionFakeRepository();

        AddReunionDialogFragment fragment = AddReunionDialogFragment.newInstance();

        new AddReunionDialogPresenter(fragment, model);

        return fragment;

    }

}
