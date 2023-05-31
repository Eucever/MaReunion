package com.example.mareunion.utils;

import android.view.View;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;

import com.example.mareunion.R;

import org.hamcrest.Matcher;

public class DropViewAction implements ViewAction {
    /**
     * A mechanism for ViewActions to specify what type of views they can operate on.
     * @return a Matcher that will be used to filter the views available for this ViewAction.
     */
    @Override
    public Matcher<View> getConstraints() {
        return null;
    }

    /**
     * Description of the ViewAction.
     * @return a String that will be used to describe the ViewAction in error messages.
     */
    @Override
    public String getDescription() {
        return "Click on drop button";
    }

    /**
     * Perform the ViewAction on the given View.
     * @param uiController the UiController to use to interact with the UI.
     * @param view the View to perform the ViewAction on.
     */
    @Override
    public void perform(UiController uiController, View view) {
        // find the UI drop button on a specific meeting
        View button = view.findViewById(R.id.meeting_delete_button);
        // do perform the click
        button.performClick();
    }
}
