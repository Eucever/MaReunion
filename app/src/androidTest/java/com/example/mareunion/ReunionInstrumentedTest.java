package com.example.mareunion;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.assertThat;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

import static com.example.mareunion.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.notNullValue;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.mareunion.utils.BaseTestInstrumentalised;
import com.example.mareunion.utils.DropViewAction;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ReunionInstrumentedTest extends BaseTestInstrumentalised {

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

        onView(allOf(withId(R.id.meeting_addperson_button)))
                .perform(click());

        onView(allOf(withId(R.id.editMail),isDisplayed()))
                .perform(replaceText("adresse1@gmail.com"),closeSoftKeyboard());

        onView(allOf(withId(R.id.btnAddperson)))
                .perform(click());
        onView(allOf(withId(R.id.meeting_creation_dialog_toolbar_save_item)))
                .perform(click());

        onView(allOf(withId(R.id.meeting_creation_dialog_toolbar_save_item)))
                .perform(click());

        onView(allOf(withId(R.id.reunion_list_recyclerview),isDisplayed()))
                .check(withItemCount(EXPECTED_COUNT_AFTERCREATION));

        }
    @Test
    public void meetingListTest_CreateMeeting_ShouldNotCreateReu_IfSujetIsEmpty(){
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

        onView(allOf(withId(R.id.meeting_creation_dialog_toolbar_save_item)))
                .perform(click());

        onView(allOf(withId(R.id.addMeeting_cardView)))
                .check(matches(isDisplayed()));

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
