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
        //traitement de l'action sur le bouton timepicker

        /*

        btnTimepicker.setOnClickListener(view -> {
            TimePickerFactory factory= new TimePickerFactory();

            TimePickerFragment fragment = factory.getFragment(DateEasy.now(),(timePicked)->{
                Log.i("debug","Instant selection par l'utilisateur " + timePicked);
            });
            fragment.display(fm);
        });

        btnDatepicker.setOnClickListener(view -> {
            DatePickerFactory factory = new DatePickerFactory();

            DatePickerFragment fragment = factory.getFragment(DateEasy.now(),DateEasy.now(),true,(datePicked)->{
                Log.i("debug","Instant selection par l'utilisateur " + datePicked);
            });
            fragment.display(fm);
        });
        btnAddParticipants.setOnClickListener(view -> {
            AddParticipantsDialogFactory factory = new AddParticipantsDialogFactory();

            Set<Participant> participantSet = new TreeSet<>();

            AddParticipantsDialogFragment fragment = factory.getFragment(participantSet, (participantSet2)->{
                participantSet2.stream().map(x->x.getMail()).forEach(System.out::println);
            });
            fragment.display(fm);
        });

        btnAddReunion.setOnClickListener(view -> {
            AddReunionDialogFactory factory = new AddReunionDialogFactory();

            AddReunionDialogFragment fragment = factory.getFragment();

            fragment.display(fm);
        });

        btnMeetingList.setOnClickListener(v -> {
            // create the fragment for the meetings list
            ReunionsListFragment f = ReunionsListFragment.newInstance();
            // create the model for the meetings list
            ReunionsListFakeRepository m = new ReunionsListFakeRepository();
            // create the presenter for the meetings list
            ReunionsListPresenter p = new ReunionsListPresenter(f, m);
            // display the fragment
            fm
                    .beginTransaction().add(R.id.activity_meetings,f).commit();
        });

        */
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