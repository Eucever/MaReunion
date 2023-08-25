package com.example.mareunion;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.hasMinimumChildCount;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static com.example.mareunion.utils.RecyclerViewItemCountAssertion.withItemCount;
import static org.hamcrest.Matchers.allOf;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.mareunion.utils.BaseTestInstrumentalised;
import com.example.mareunion.utils.DropViewAction;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class FilterReunionTest extends BaseTestInstrumentalised {

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
