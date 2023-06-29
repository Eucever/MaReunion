package com.example.mareunion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.mareunion.di.DI;
import com.example.mareunion.model.Participant;
import com.example.mareunion.model.Reunion;
import com.example.mareunion.repository.ReunionsListFakeRepository;
import com.example.mareunion.ui.addparticipants.AddParticipantsDialogFactory;
import com.example.mareunion.ui.addparticipants.AddParticipantsDialogFragment;
import com.example.mareunion.ui.addreunions.AddReunionDialogFactory;
import com.example.mareunion.ui.addreunions.AddReunionDialogFragment;
import com.example.mareunion.ui.datepicker.DatePickerFactory;
import com.example.mareunion.ui.datepicker.DatePickerFragment;
import com.example.mareunion.ui.reunionlists.ReunionsListFragment;
import com.example.mareunion.ui.reunionlists.ReunionsListPresenter;
import com.example.mareunion.ui.timepicker.TimePickerFactory;
import com.example.mareunion.ui.timepicker.TimePickerFragment;
import com.example.mareunion.ui.utils.DateEasy;

import java.util.Set;
import java.util.TreeSet;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {


    @BindView(R.id.addMeeting)
    Button btnAddReunion;

    private ReunionsListPresenter mReunionListpresenter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final FragmentManager fm = getSupportFragmentManager();

        ReunionsListFragment mReunionsListFragment = (ReunionsListFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.activity_meetings);

        // if the fragment doesn't exist, create it
        if (mReunionsListFragment == null) {
            // create the fragment
            mReunionsListFragment = ReunionsListFragment.newInstance();
            // add the fragment to the activity
            getSupportFragmentManager().beginTransaction().add(R.id.activity_meetings, mReunionsListFragment)
                    .commit();
        }

        // create the model
        ReunionsListFakeRepository meetingsListFakeRepository = new ReunionsListFakeRepository();

        // create the presenter
        mReunionListpresenter  = new ReunionsListPresenter(mReunionsListFragment, meetingsListFakeRepository);
    }

    public void updateReunionsFragments() {
        //DI.getReunionApiService().getReunions().stream().map(x -> x.getSujet()).forEach(System.out::println);
        mReunionListpresenter.onRefreshReunionsListRequested();
    }
}