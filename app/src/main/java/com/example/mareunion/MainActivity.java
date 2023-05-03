package com.example.mareunion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.example.mareunion.ui.datepicker.DatePickerFactory;
import com.example.mareunion.ui.datepicker.DatePickerFragment;
import com.example.mareunion.ui.timepicker.TimePickerFactory;
import com.example.mareunion.ui.timepicker.TimePickerFragment;
import com.example.mareunion.utils.DateEasy;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.timepicker)
    Button btnTimepicker;

    @BindView(R.id.datePicker)
    Button btnDatepicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final FragmentManager fm= getSupportFragmentManager();
        //traitement de l'action sur le bouton timepicker

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
    }
}