package com.example.mareunion.utils;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.notNullValue;

import androidx.test.ext.junit.rules.ActivityScenarioRule;

import com.example.mareunion.MainActivity;
import com.example.mareunion.R;
import com.example.mareunion.di.DI;
import com.example.mareunion.model.Lieu;
import com.example.mareunion.model.Participant;
import com.example.mareunion.model.Reunion;
import com.example.mareunion.service.ReunionApiService;
import com.example.mareunion.ui.utils.DateEasy;

import org.junit.Before;
import org.junit.Rule;

import java.time.Instant;

public class BaseTestInstrumentalised {
    protected MatcherFactory matcherFactory = new MatcherFactory();

    protected Reunion reu1;

    protected Reunion reu2;

    protected Reunion reu3;

    @Rule
    public ActivityScenarioRule<MainActivity> mainActivityTestRule =
            new ActivityScenarioRule<>(MainActivity.class);

    protected ReunionApiService mService;
    @Before
    public void setUp(){
        mainActivityTestRule.getScenario().onActivity(activity -> {
            assertThat(activity, notNullValue());
        });

        mService = DI.getReunionApiService();

        assertThat(mService, notNullValue());

        Participant participant1 = new Participant("partcip1@gmail.com");
        Participant participant2 = new Participant("partcip2@outlook.fr");
        Participant participant3 = new Participant("partcip3@laposte.net");
        Participant participant4 = new Participant("partcip4@gmail.com");


        Lieu lieu1 = new Lieu("Salle A1");
        Lieu lieu2 = new Lieu("Salle A2");
        Lieu lieu3 = new Lieu("Salle B1");

        Instant now = DateEasy.now();
        Instant date1 = DateEasy.plusDays(now, 10);
        Instant date2 = DateEasy.plusDays(now, 11);
        Instant date3 = DateEasy.plusDays(now, 15);

        reu1 = new Reunion(date1,"Reunion Infos",lieu1,participant1,participant3,participant4);
        reu2 = new Reunion(date2,"Conference",lieu2,participant3,participant1,participant4);
        reu3 = new Reunion(date3,"2eme conference",lieu3,participant1,participant2,participant3,participant4);

        mService.getReunions().clear();
        mService.addReunion(reu1);
        mService.addReunion(reu2);
        mService.addReunion(reu3);

        //Solution de contournement pour donner le focus sur le premier champs
        onView(allOf(withId(R.id.button_open_filtre), isDisplayed()))
                .perform(click());
        onView(allOf(withId(R.id.button_close_filtre), isDisplayed()))
                .perform(click());
    }
}
