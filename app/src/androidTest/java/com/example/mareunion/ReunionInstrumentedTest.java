package com.example.mareunion;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;

import static com.example.mareunion.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.notNullValue;

import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.mareunion.di.DI;
import com.example.mareunion.model.Lieu;
import com.example.mareunion.model.Participant;
import com.example.mareunion.model.Reunion;
import com.example.mareunion.service.ReunionApiService;
import com.example.mareunion.ui.utils.DateEasy;
import com.example.mareunion.utils.DropViewAction;
import com.example.mareunion.utils.MatcherFactory;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.Instant;

@RunWith(AndroidJUnit4.class)
public class ReunionInstrumentedTest {

    private MatcherFactory matcherFactory = new MatcherFactory();

    private Reunion reu1;

    private Reunion reu2;

    private Reunion reu3;

    @Rule
    public ActivityScenarioRule<MainActivity>mainActivityTestRule =
            new ActivityScenarioRule<>(MainActivity.class);

    private ReunionApiService mService;
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

    @Test
    public void meetingListTest_ShouldNotBeEmpty(){
        onView(allOf(withId(R.id.reunion_list_recyclerview), isDisplayed()))
                .check(matches(hasMinimumChildCount(1)));
    }

    @Test
    public void meetingListTest_ShouldCountThreeReu(){
        onView(allOf(withId(R.id.reunion_list_recyclerview), isDisplayed()))
                .check(withItemCount(3));
    }
    @Test
    public void meetingListTest_RemoveButton_ShouldRemoveReu(){
        onView(allOf(withId(R.id.reunion_list_recyclerview),isDisplayed()))
                .perform(actionOnItemAtPosition(2, new DropViewAction()))
                .check(withItemCount(2));
    }
    @Test
    public void meetingListTest_CreateMeeting_ShouldCreateReu(){
        final int EXPECTED_COUNT_AFTERCREATION = 4;
        onView(allOf(withId(R.id.addMeeting),isDisplayed()))
                .perform(click());

        onView(allOf(
                matcherFactory.childAtPosition(
                        matcherFactory.childAtPosition(
                                withId(R.id.addMeeting_LieuTextInput), 0
                        ),
                        0
                ),
                isDisplayed()
        ))
                .perform(replaceText("A place"), closeSoftKeyboard());

        onView(allOf(
                matcherFactory.childAtPosition(
                        matcherFactory.childAtPosition(
                                withId(R.id.addMeeting_SujetTextInput), 0
                        ),
                        0
                ),
                isDisplayed()))
                .perform(replaceText("Sujet 55"),closeSoftKeyboard());

        onView(allOf(withId(R.id.meeting_creation_dialog_toolbar_save_item)))
                .perform(click());

        onView(allOf(withId(R.id.reunion_list_recyclerview),isDisplayed()))
                .check(withItemCount(EXPECTED_COUNT_AFTERCREATION));

        }

    @Test
    public void meetingListTest_FilterMeeting(){
        final int EXPECTED_COUNT_AFTERFILTER = 1;
        onView(allOf(withId(R.id.reunion_filtre_cardview),isDisplayed()))
                .perform(click());

        onView(allOf(
                matcherFactory.childAtPosition(
                        matcherFactory.childAtPosition(
                                withId(R.id.input_lieu), 0
                        ),
                        0),
                isDisplayed()
        ))
                // set the place filter to Salle
                .perform(replaceText("Salle A1"), closeSoftKeyboard());

        // validate the filters
        onView(allOf(withId(R.id.button_apply), isDisplayed()))
                .perform(click());

        // check the count output
        onView(allOf(withId(R.id.reunion_list_recyclerview), isDisplayed()))
                .check(withItemCount(EXPECTED_COUNT_AFTERFILTER));


        }
    }
