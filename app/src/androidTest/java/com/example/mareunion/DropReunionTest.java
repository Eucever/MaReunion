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
public class DropReunionTest extends BaseTestInstrumentalised {

    @Test
    public void meetingListTest_RemoveButton_ShouldRemoveReu(){
        onView(allOf(withId(R.id.reunion_list_recyclerview),isDisplayed()))
                .perform(actionOnItemAtPosition(2, new DropViewAction()))
                .check(withItemCount(2));
    }
}
