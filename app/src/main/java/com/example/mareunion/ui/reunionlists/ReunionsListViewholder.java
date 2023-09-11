package com.example.mareunion.ui.reunionlists;

import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareunion.R;
import com.example.mareunion.model.Participant;
import com.example.mareunion.model.Reunion;
import com.example.mareunion.ui.utils.DateEasy;
import com.example.mareunion.ui.utils.ParticipantsListFormatter;

import java.util.Set;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ReunionsListViewholder extends RecyclerView.ViewHolder {
    private Reunion mMeeting;

    /**
     * UI Components
     */

    // subject of the meeting label
    @BindView(R.id.meeting_sujet_text)
    TextView mSubjectText;

    // button to drop a meeting
    @BindView(R.id.meeting_delete_button)
    ImageButton mDeleteButton;

    // date of the meeting label
    @BindView(R.id.meeting_date_text)
    TextView mDateText;

    // place of the meeting label
    @BindView(R.id.meeting_lieu_text)
    TextView mPlaceText;

    // expandable card view containing the persons invited to the meeting (expand button)
    @BindView(R.id.person_list_open_button)
    ImageButton mExpandButton;

    // expandable card view containing the persons invited to the meeting (collapse button)
    @BindView(R.id.person_list_close_button)
    ImageButton mCollapseButton;

    // list of the persons invited to the meeting label
    @BindView(R.id.meeting_persons_list)
    TextView mPersonsFlattenListText;

    //number of person in the list
    @BindView(R.id.nbParticipant_text)
    TextView mPersonsNumberText;

    // Empty meeting (no persons invited yet) string
    @BindString(R.string.empty_meeting_persons_list)
    String mEmptyMeetingInvitedPersonsList;

    // expandable card view containing the persons invited to the meeting (card view)
    @BindView(R.id.meeting_item_cardview)
    CardView mCardView;

    /**
     * Constructor
     *
     * @param itemView the view of the item
     * @param onClickListener the listener to be called when the delete button is clicked
     */
    public ReunionsListViewholder(@NonNull View itemView,
                                  ReunionsListAdapter.DropClickListener onClickListener) {
        // always call super constructor
        super(itemView);
        // bind the ui components to java code
        ButterKnife.bind(this, itemView);
        // call the listener when the delete button is clicked
        mDeleteButton.setOnClickListener(
                v -> onClickListener.onClick(v, getLayoutPosition())
        );
        // call the listener when the expand button is clicked
        mExpandButton.setOnClickListener(v -> expandOrCollapseInvitedPersons());
        // call the listener when the collapse button is clicked
        mCollapseButton.setOnClickListener(v -> expandOrCollapseInvitedPersons());
        // set the click listener on the whole item (show/hide given meeting details)
        itemView.setOnClickListener(v -> expandOrCollapseInvitedPersons());
    }

    /**
     * Set the meeting to be displayed as item of the recycler view
     * @param reunion the meeting to be displayed as item of the recycler view
     */
    public void setMeeting(Reunion reunion) {
        // set the meeting to display
        mMeeting = reunion;
        // update the ui accordingly (date, subject, place)
        mDateText.setText(DateEasy.localeSpecialStringFromInstant(reunion.getDate()));
        mSubjectText.setText(reunion.getSujet());
        mSubjectText.setContentDescription(reunion.getSujet());
        mPlaceText.setText(reunion.getLieu().getEndroit());
        mPlaceText.setContentDescription(reunion.getLieu().getEndroit());
        // update the persons invited to the meeting list
        setPersonsList();
    }

    /**
     * Handle the expandable card view, that display the persons list
     */
    private void expandOrCollapseInvitedPersons() {
        // if the card view is collapsed, expand it
        if (mPersonsFlattenListText.getVisibility() == View.GONE) {
            // show the persons list
            mPersonsFlattenListText.setVisibility(View.VISIBLE);
            // show the collapse button
            mCollapseButton.setVisibility(View.VISIBLE);
            // hide the expand button
            mExpandButton.setVisibility(View.GONE);
        } else {
            // else collapse it
            mPersonsFlattenListText.setVisibility(View.GONE);
            // hide the collapse button
            mCollapseButton.setVisibility(View.GONE);
            // show the expand button
            mExpandButton.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Set the list of persons invited to the meeting in the UI
     */
    private void setPersonsList() {
        // get persons
        Set<Participant> participants = mMeeting.getParticipants();
        int nbParticipants = participants.size();
        // create persons formatter
        ParticipantsListFormatter personsListFormatter = new ParticipantsListFormatter(participants);

        mPersonsFlattenListText.setText(personsListFormatter.format());
        mPersonsNumberText.setText(""+nbParticipants);

    }
}
