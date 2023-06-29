package com.example.mareunion.ui.reunionlists;

import static com.google.common.base.Preconditions.checkNotNull;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareunion.R;
import com.example.mareunion.model.Reunion;
import com.example.mareunion.ui.addreunions.AddReunionDialogFactory;
import com.example.mareunion.ui.datepicker.DatePickerFactory;
import com.example.mareunion.ui.datepicker.DatePickerFragment;
import com.example.mareunion.ui.utils.SimpleTextWatcherFactory;
import com.google.android.material.textfield.TextInputLayout;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReunionsListFragment extends Fragment implements ReunionsListContract.View {
    private static final String TAG = "MeetingsListFragment";

    /**
     * Meetings list presenter
     */
    private ReunionsListContract.Presenter mPresenter;

    /**
     * Meetings list adapter
     */
    private ReunionsListAdapter mMeetingsListAdapter;

    /**
     * Ui components
     */

    // the recycler view to display the list of the meetings
    @BindView(R.id.reunion_list_recyclerview)
    RecyclerView mRecyclerView;

    // the card view to allow the user to filter the meetings list
    @BindView(R.id.reunion_filtre_cardview)
    CardView mFilterCardView;

    // the filters expand button
    @BindView(R.id.button_open_filtre)
    ImageButton mFilterOpenButton;

    // the filters collapse button
    @BindView(R.id.button_close_filtre)
    ImageButton mFilterCloseButton;

    // the filters apply button
    @BindView(R.id.button_apply)
    Button mFilterApplyButton;

    // the place text input filter
    @BindView(R.id.input_lieu)
    TextInputLayout mFilterPlaceTextInput;

    // the start date text input filter
    @BindView(R.id.input_start_date)
    TextInputLayout mFilterStartDateTextInput;

    // the end date text input filter
    @BindView(R.id.input_end_date)
    TextInputLayout mFilterEndDateTextInput;

    public ReunionsListFragment() {
        // always call the super constructor
        super();
    }

    /**
     * Create a new instance of the fragment
     *
     * @return the fragment
     */
    public static ReunionsListFragment newInstance() {
        return new ReunionsListFragment();
    }

    /**
     * Attach the presenter, to avoid circular dependency issue
     * (the view need the presenter to instantiate, and the presenter need the view to instantiate)
     * @param presenter the presenter
     */
    @Override
    public void attachPresenter(@NonNull ReunionsListContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_meetings_list, container, false);
        // Bind the ui components
        ButterKnife.bind(this, view);
        // Find the floating action button (add a meeting), then add the setOnClickListener logic
        Objects.requireNonNull(getActivity())
                .findViewById(R.id.addMeeting)
                .setOnClickListener(v -> mPresenter.onCreateReunionRequested());
        // Build the recycler view
        mMeetingsListAdapter = new ReunionsListAdapter(
                // empty list of meetings at startup
                new ArrayList<>(),
                // the action on click to drop a meeting
                (v, position) -> mPresenter.dropReunionRequested(position)
        );
        // Set the layout manager of the recycler view
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Set the adapter of the recycler view
        mRecyclerView.setAdapter(mMeetingsListAdapter);
        // Configure the initial filters
        configureFilters();
        // Return the view
        return view;
    }

    @Override
    public void setErrorFilterStartDate() {
        mFilterStartDateTextInput.setError("Please use dd/mm/yy format or leave empty");
    }

    @Override
    public void setErrorFilterEndDate() {
        mFilterEndDateTextInput.setError("Please use the dd/mm/yy format or leave empty");
    }

    /**
     * Configure filters
     */
    private void configureFilters() {

        // Build a listener that trigger the presenter onFiltersChanged method
        View.OnClickListener listener = v -> mPresenter.onFiltersChanged(
                // Set the place text input filter value
                Objects.requireNonNull(mFilterPlaceTextInput.getEditText())
                        .getText()
                        .toString(),
                // Set the start date text input filter value
                Objects.requireNonNull(mFilterStartDateTextInput.getEditText())
                        .getText()
                        .toString(),
                // Set the end date text input filter value
                Objects.requireNonNull(mFilterEndDateTextInput.getEditText())
                        .getText()
                        .toString()
        );


        // On apply button click, set the listener just created
        mFilterApplyButton.setOnClickListener(listener);
        // On card view click, set the listener just created
        mFilterCardView.setOnClickListener(listener);
        // On expand button, set the listener just created
        mFilterOpenButton.setOnClickListener(listener);
        // On collapse button, set the listener just created
        mFilterCloseButton.setOnClickListener(listener);

        // Configure the text input filters
        configureStartDateTextInput();
        configureEndDateTextInput();
        configurePlaceTextInput();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void configurePlaceTextInput() {

        // on focus change on the place text input filter, then notify the presenter accordingly
        Objects.requireNonNull(mFilterPlaceTextInput.getEditText()).setOnFocusChangeListener(
                (v, hasFocus) -> {
                    final EditText editText = mFilterPlaceTextInput.getEditText();
                    // notify the presenter that the place text input filter might have changed
                    mPresenter.saveFilterPlace(editText.getText().toString());
                }
        );

    }

    @SuppressLint("ClickableViewAccessibility")
    private void configureStartDateTextInput() {

        // on focus change on the start date text input filter, then notify the presenter accordingly
        Objects.requireNonNull(mFilterStartDateTextInput.getEditText())
                .setOnFocusChangeListener((v, hasFocus) -> {
                    final EditText editText = mFilterStartDateTextInput.getEditText();
                    if (hasFocus) {
                        // if we have focus, reset the error
                        mFilterStartDateTextInput.setErrorEnabled(false);
                        mPresenter.setFilterStartDate(editText.getText().toString());
                    } else {
                        // we use a different call the presenter, to notify the presenter that the
                        // we don't need to trigger the date picker dialog anymore
                        mPresenter.setFilterStartDateManual(editText.getText().toString());
                    }
                });

        // on start date changed, notify the presenter accordingly
        mFilterStartDateTextInput.getEditText().setOnClickListener(
                v -> mPresenter.setFilterStartDate(
                        mFilterStartDateTextInput.getEditText().getText().toString()
                )
        );
    }

    @SuppressLint("ClickableViewAccessibility")
    private void configureEndDateTextInput() {

        // on focus change on the start date text input filter, then notify the presenter accordingly
        Objects.requireNonNull(mFilterEndDateTextInput.getEditText())
                .setOnFocusChangeListener((v, hasFocus) -> {
                    final EditText editText = mFilterEndDateTextInput.getEditText();
                    if (hasFocus) {
                        // if we have focus, reset the error
                        mFilterEndDateTextInput.setErrorEnabled(false);
                        mPresenter.setFilterEndDate(editText.getText().toString());
                    } else {
                        // we use a different call the presenter, to notify the presenter that the
                        // we don't need to trigger the date picker dialog anymore
                        mPresenter.setFilterEndDateManual(editText.getText().toString());
                    }
                });


        // on start date changed, notify the presenter accordingly
        mFilterEndDateTextInput.getEditText().setOnClickListener(
                v -> mPresenter.setFilterStartDate(
                        mFilterEndDateTextInput.getEditText().getText().toString()
                )
        );
    }


    @Override
    public void onResume() {
        super.onResume();
        mPresenter.init();
    }

    @Override
    public void updateReunions(List<Reunion> reunions) {
        mMeetingsListAdapter.updateReunions(reunions);
    }

    /**
     * Method called by the presenter, to display the meeting registration (add) dialog
     */
    @Override
    public void triggerReunionRegistrationDialog() {
        AddReunionDialogFactory factory = new AddReunionDialogFactory();
        factory
                .getFragment()
                .display(getFragmentManager());
    }

    /**
     * Expand or collapse the filters
     */
    @Override
    public void expandOrCollapseFilters() {
        if (mFilterOpenButton.getVisibility() == View.VISIBLE) {
            mFilterOpenButton.setVisibility(View.GONE);
            mFilterStartDateTextInput.setVisibility(View.VISIBLE);
            mFilterEndDateTextInput.setVisibility(View.VISIBLE);
            mFilterApplyButton.setVisibility(View.VISIBLE);
            mFilterPlaceTextInput.setVisibility(View.VISIBLE);
            mFilterCloseButton.setVisibility(View.VISIBLE);
        } else {
            mFilterStartDateTextInput.setVisibility(View.GONE);
            mFilterCloseButton.setVisibility(View.GONE);
            mFilterApplyButton.setVisibility(View.GONE);
            mFilterEndDateTextInput.setVisibility(View.GONE);
            mFilterPlaceTextInput.setVisibility(View.GONE);
            mFilterOpenButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Update the filters text input label, and reset the icons around every filter text input
     * Update also the cursor position in the text input, to meet the user expectations
     * @param filterPlace the place filter
     * @param filterStartDate the start date filter
     * @param filterEndDate the end date filter
     */
    @Override
    public void updateFilters(String filterPlace, String filterStartDate, String filterEndDate) {
        // update the place text filter
        Objects.requireNonNull(mFilterPlaceTextInput.getEditText()).setText(filterPlace);
        // update the start date text filter
        Objects.requireNonNull(mFilterStartDateTextInput.getEditText()).setText(
                filterStartDate == null ? "": filterStartDate
        );
        // update the end date text filter
        Objects.requireNonNull(mFilterEndDateTextInput.getEditText()).setText(
                filterEndDate == null ? "" : filterEndDate
        );

        // put the selection cursor at the end of the text
        mFilterPlaceTextInput.getEditText().setSelection(
                mFilterPlaceTextInput.getEditText().getText().length()
        );
        mFilterStartDateTextInput.getEditText().setSelection(
                mFilterStartDateTextInput.getEditText().getText().length()
        );
        mFilterEndDateTextInput.getEditText().setSelection(
                mFilterEndDateTextInput.getEditText().getText().length()
        );
    }

    /**
     * Change icons around the text input layout
     * @param textInputLayout the text input layout
     * @param drawableStart the start icon
     * @param drawableEnd the end icon
     */
    private void setCompoundDrawables(TextInputLayout textInputLayout,
                                      Drawable drawableStart,
                                      Drawable drawableEnd
    ){

        Objects.requireNonNull(textInputLayout.getEditText())
                .setCompoundDrawablesRelativeWithIntrinsicBounds(
                        drawableStart,
                        null,
                        drawableEnd,
                        null
                );

    }

    /**
     * Method called by the presenter, to display the date picker dialog
     * @param date the date to display
     * @param isBegin true if the date is the start date, false if the date is the end date
     */
    @Override
    public void triggerDatePickerDialog(Instant date, boolean isBegin) {
        // create the date picker factory
        DatePickerFactory factory = new DatePickerFactory();
        // get the fragment ..
        DatePickerFragment fragment = factory.getFragment(
                date,
                null,
                !isBegin,
                // on date set, notify the presenter
                (datePicked) -> mPresenter.saveFilterDate(datePicked, isBegin)
        );
        // .. and display it
        fragment.display(getFragmentManager());
    }

}
