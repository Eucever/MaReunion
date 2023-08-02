package com.example.mareunion.ui.addreunions;

import static com.google.common.base.Preconditions.checkNotNull;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.mareunion.MainActivity;
import com.example.mareunion.R;
import com.example.mareunion.model.Participant;
import com.example.mareunion.ui.addparticipants.AddParticipantsDialogDisplayable;
import com.example.mareunion.ui.addparticipants.AddParticipantsDialogFactory;
import com.example.mareunion.ui.datepicker.DatePickerFactory;
import com.example.mareunion.ui.datepicker.DatePickerFragment;
import com.example.mareunion.ui.timepicker.TimePickerFactory;
import com.example.mareunion.ui.timepicker.TimePickerFragment;
import com.example.mareunion.ui.utils.DateEasy;
import com.example.mareunion.ui.utils.SimpleTextWatcher;
import com.google.android.material.textfield.TextInputLayout;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddReunionDialogFragment extends DialogFragment implements AddReunionDialogContract.View{

    private static final String TAG = "AddReunionDialogFragment";

    private static final int TEXT_INPUT_MIN_LENGTH = 0;

    private AddReunionDialogContract.Presenter mPresenter;

    private FragmentManager mFragmentManager;

    @BindView(R.id.addMeeting_Toolbar)
    Toolbar mRegistrationToolbar;

    @BindView(R.id.addMeeting_SujetTextInput)
    TextInputLayout mSujetTextInput;

    @BindView(R.id.addMeeting_LieuTextInput)
    TextInputLayout mLieuTextInput;

    @BindView(R.id.addMeeting_DateTextInput)
    TextInputLayout mDateTextInput;

    @BindView(R.id.addMeeting_cardView)
    CardView mParticipantsCardView;

    @BindView(R.id.addMeeting_personList)
    TextView mParticipantsFullListText;

    @BindView(R.id.meeting_addperson_button)
    ImageButton mAddParticipantsButton;

    public AddReunionDialogFragment(){
        super();
    }

    public static AddReunionDialogFragment newInstance(){
        return new AddReunionDialogFragment();
    }

    @Override
    public void attachPresenter(@NonNull AddReunionDialogContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }

    @Override
    public void showDialog() {
        show(mFragmentManager, TAG);
    }

    public void display(FragmentManager fragmentManager) {
        mFragmentManager = checkNotNull(fragmentManager);
        mPresenter.init();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_LightDialog);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        View view = inflater.inflate(R.layout.add_meeting_fragment, container,
                false);

        ButterKnife.bind(this, view);

        configureSujetTextInput();

        configureLieuTextInput();

        configureDateTextInput();

        configureAddParticipants();

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        mRegistrationToolbar.inflateMenu(R.menu.meeting_registration_menu);

        mRegistrationToolbar.setNavigationOnClickListener(v -> dismiss());

        mRegistrationToolbar.setOnMenuItemClickListener(item -> {
            mPresenter.onCreateReunionRequest(

                    Objects.requireNonNull(mSujetTextInput.getEditText()).getText().toString(),

                    Objects.requireNonNull(mDateTextInput.getEditText()).getText().toString(),

                    Objects.requireNonNull(mLieuTextInput.getEditText()).getText().toString()
            );
            return true;
        });
    }

    private void configureSujetTextInput() {
        Objects.requireNonNull(mSujetTextInput.getEditText())
                .addTextChangedListener(new SimpleTextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s) {

                        if (mSujetTextInput.getEditText().getText().length() > TEXT_INPUT_MIN_LENGTH) {
                            mSujetTextInput.setErrorEnabled(false);
                        }
                    }
                });
    }
    private void configureLieuTextInput() {

        Objects.requireNonNull(mLieuTextInput.getEditText())
                .addTextChangedListener(new SimpleTextWatcher() {
                    @Override
                    public void afterTextChanged(Editable s) {

                        if (mLieuTextInput.getEditText().getText().length() > TEXT_INPUT_MIN_LENGTH) {
                            mLieuTextInput.setErrorEnabled(false);
                        }
                    }
                });
    }

    private void configureDateTextInput() {
        Objects.requireNonNull(mDateTextInput.getEditText()).setOnClickListener(
                v -> mPresenter.onReunionDatePickRequest(
                        mDateTextInput.getEditText().getText().toString()
                )
        );

        mDateTextInput.getEditText().setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                mDateTextInput.setErrorEnabled(false);
                mPresenter.onReunionDatePickRequest(
                        mDateTextInput.getEditText().getText().toString()
                );
            }
        });

        mDateTextInput.getEditText().addTextChangedListener(new SimpleTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                if (mDateTextInput.getEditText().getText().length() > TEXT_INPUT_MIN_LENGTH) {

                    mDateTextInput.setErrorEnabled(false);
                }
            }
        });

        mDateTextInput.getEditText().setText(DateEasy.localeDateTimeStringFromNow());
    }

    private void configureAddParticipants() {
        mAddParticipantsButton.setOnClickListener(v -> mPresenter.onAddParticipantsRequest());
    }

    @Override
    public void onResume() {
        super.onResume();
        mPresenter.onResumeRequest();
    }


    @Override
    public void onDismiss(@NonNull DialogInterface dialogInterface) {
        ((MainActivity) Objects.requireNonNull(getContext())).updateReunionsFragments();
    }




    @Override
    public void returnBackToReunions() {
        dismiss();
    }

    @Override
    public void updateReunionDate(Instant reunionDate) {
        Objects.requireNonNull(mDateTextInput
                        .getEditText())
                .setText(DateEasy.localeDateTimeStringFromInstant(reunionDate));
    }

    @Override
    public void updateParticipantsInvitedToTheReunion(String participantsFlattenList) {
        mParticipantsFullListText.setText(participantsFlattenList);
        mParticipantsFullListText.setVisibility(View.VISIBLE);
    }

    @Override
    public void setErrorSujetIsEmpty() {
        mSujetTextInput.setError("Must be set");
    }

    @Override
    public void setErrorLieuIsEmpty() {
        mLieuTextInput.setError("Must be set");
    }

    @Override
    public void setErrorDateIsEmpty() {
        mDateTextInput.setError("Must be set");
    }

    @Override
    public void setErrorDateIsInWrongFormat() {
        mDateTextInput.setError("Date in wrong format");
    }
    @Override
    public void setErrorParticipantsListIsEmpty(){mParticipantsFullListText.setError("List Is empty");
    }

    @Override
    public void triggerDatePickerDialog(Instant reunionDate) {
        DatePickerFactory factory = new DatePickerFactory();

        DatePickerFragment fragment = factory.getFragment(

                reunionDate,

                DateEasy.now(),

                false,

                (date) -> mPresenter.onReunionDateSelected(date)
        );

        fragment.display(getFragmentManager());
    }

    @Override
    public void triggerTimePickerDialog(Instant reunionDate) {
        TimePickerFactory factory = new TimePickerFactory();

        TimePickerFragment fragment = factory.getFragment(
                reunionDate,
                (date) -> mPresenter.onReunionTimeSelected(date)
        );

        fragment.display(getFragmentManager());
    }

    @Override
    public void triggerAddParticipantsDialog(Set<Participant> initialParticipants) {
        AddParticipantsDialogFactory factory = new AddParticipantsDialogFactory();

        AddParticipantsDialogDisplayable fragment = factory
                .getFragment(

                        initialParticipants,

                        (participants) -> mPresenter.onParticipantsChanged(participants)
                );

        fragment.display(getFragmentManager());

    }
}
