package com.example.mareunion.ui.addparticipants;


import static com.google.common.base.Preconditions.checkNotNull;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.material.button.MaterialButton;

import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.mareunion.R;
import com.example.mareunion.model.Participant;
import com.example.mareunion.ui.utils.ParticipantsListFormatter;
import com.example.mareunion.ui.utils.SimpleTextWatcher;

import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddParticipantsDialogFragment extends DialogFragment implements AddParticipantsDialogContract.View, AddParticipantsDialogDisplayable {
    /**
     * Declare Class tag for logging
     */
    private static final String TAG = "AddPersonsDialogFragmentMVP";

    /**
     * Minimum characters required for an email to add that person
     */
    private static final int MINIMUM_EMAIL_LENGTH_TO_SHOW_ADD_BUTTON = 4;

    /**
     * Declare the associated presenter
     */
    private AddParticipantsDialogContract.Presenter mPresenter;

    /**
     * Buffer the fragment manager
     */
    private FragmentManager mFragmentManager;

    /**
     * Bind UI components
     */

    // represent the UI toolbar (with the "save" button)
    @BindView(R.id.add_person_toolbar)
    Toolbar mAddPersonsToolbar;

    // represent the "add single person" button next to the UI text input
    @BindView(R.id.btnAddperson)
    MaterialButton mAddPersonsBtn;

    // represent the UI text input
    @BindView(R.id.editMail)
    AppCompatEditText mAddPersonsTextInput;

    // represent the UI text view for displaying the list of persons
    @BindView(R.id.txtListPerson)
    TextView mAddPersonsFullListText;

    /**
     * Constructor
     */
    public AddParticipantsDialogFragment() {
        // always call the super class constructor
        super();
    }

    /**
     * Create a new instance
     */
    public static AddParticipantsDialogFragment newInstance() {
        return new AddParticipantsDialogFragment();
    }

    /**
     * Attach the presenter, to avoid circular dependency issue
     * (the view need the presenter to instantiate, and the presenter need the view to instantiate)
     * @param presenter the presenter
     */
    @Override
    public void attachPresenter(AddParticipantsDialogContract.Presenter presenter) {
        mPresenter = presenter;
    }

    /**
     * Implements the display method, to show the UI
     */
    @Override
    public void display(FragmentManager fragmentManager) {
        mFragmentManager = checkNotNull(fragmentManager);
        // signal the presenter we want to init and display the dialog
        mPresenter.init();
    }

    /**
     * Show the UI
     */
    @Override
    public void showDialog() {
        show(mFragmentManager, TAG);
    }


    /**
     * Build the full list of persons as string, when the presenter request it
     */
    @Override
    public void updateParticipantsList(Set<Participant> participantsSet) {
        boolean isError = false;
        // if the person set is empty, display a message
        if(participantsSet.isEmpty()){
            mAddPersonsFullListText.setText("");
        } else {
            // create persons formatter
            ParticipantsListFormatter ParticipantsListFormatter = new ParticipantsListFormatter(participantsSet);
            // set the list of persons in the UI
            mAddPersonsFullListText.setText(ParticipantsListFormatter.format());
        }
    }

    /**
     * We override onCreate, to define that we want to retain the state of the fragment
     * @param savedInstanceState the saved instance state
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // always call the super class method
        super.onCreate(savedInstanceState);
        // responsive purpose : if you rotate the device, the retained fragments
        // will remain there (they're not destroyed and recreated)
        setRetainInstance(true);
        // suppress the title bar, and adopt a light theme
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_LightDialog);
    }

    /**
     * We override onCreateView, to further define the behavior of the UI at startup
     * @param inflater the layout inflater
     * @param container the container
     * @param savedInstanceState the saved instance state
     * @return the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // always call the super class method
        super.onCreateView(inflater, container, savedInstanceState);
        // inflate the layout
        View view = inflater.inflate(R.layout.add_person_fragment, container, false);
        // bind the UI components
        ButterKnife.bind(this, view);
        // call the presenter that the view is ready (UI components successfully bind)
        mPresenter.onViewLoaded();
        // configure the add person text input
        configureAddPersonText();
        // configure the add person button
        configureAddPersonButton();
        // return the view
        return view;
    }

    /**
     * You should inflate your layout in onCreateView but shouldn't
     * initialize other views (like inflating the menu) in onCreateView.
     *
     * So to avoid crash, we need to call such UI method in onViewCreated
     *
     * @param view the view
     * @param savedInstanceState the saved instance state
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        // always call the super class method
        super.onViewCreated(view, savedInstanceState);
        // load (inflate) the menu into the toolbar
        mAddPersonsToolbar.inflateMenu(R.menu.add_persons_menu);
        // configure the return button on the toolbar to dismiss the dialog
        mAddPersonsToolbar.setNavigationOnClickListener(v -> dismiss());
        // configure the save button on the toolbar
        mAddPersonsToolbar.setOnMenuItemClickListener(item -> {
            // tell the presenter the list of persons will no longer change
            mPresenter.commit();
            // dismiss the dialog (return to the previous fragment)
            dismiss();
            // return true to consume the event
            return true;
        });
    }

    /**
     * Configure the add person text input
     */
    private void configureAddPersonText() {
        mAddPersonsTextInput.addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                // get the text len from the text (email) input
                int textLen = mAddPersonsTextInput.getText().length();
                // if the text len is greater than MINIMUM_EMAIL_LEN, enable the add person button
                if (textLen > MINIMUM_EMAIL_LENGTH_TO_SHOW_ADD_BUTTON) {
                    // show the add button
                    mAddPersonsBtn.setVisibility(View.VISIBLE);
                } else {
                    // hide the add button
                    mAddPersonsBtn.setVisibility(View.GONE);
                }
            }
        });
    }

    /**
     * Configure the add person button
     */
    private void configureAddPersonButton() {
        mAddPersonsBtn.setOnClickListener(new ImageButton.OnClickListener() {
            @Override
            public void onClick(View v) {
                // retrieve the email from the text input
                String email = mAddPersonsTextInput.getText().toString();
                if(email.contains("@")){
                    cleanupMailError();
                    // build the corresponding Person object from the email
                    Participant participant = new Participant(email);
                    // tell the presenter to add the person to the list
                    mPresenter.onParticipantAdded(participant);
                    // clear the text input
                    mAddPersonsTextInput.setText("");
                    // hide the add button
                    mAddPersonsBtn.setVisibility(View.GONE);
                }
                else {
                    setErrorNotValidMail();
                }

            }
        });
    }
    private void setErrorNotValidMail(){
        mAddPersonsFullListText.setError("Not Valid mail");
    }

    private void cleanupMailError(){
        mAddPersonsFullListText.setError(null);
    }
}
