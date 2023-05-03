package com.example.mareunion.core;

public interface MvpInterface {
    interface Model {
        // super interface Model, not used in this app, but keep in mind that Model is
        // always needed in the MVP pattern
    }

    // The View interface that is implemented indirectly by the Activity (or Fragment)
    // Generic type T is the Presenter type
    interface View<T> {

        // A given presenter need to be attached to the view :
        // - the view will need to call the presenter's methods when the user interacts with the UI
        // - the view is called by the presenter to update the UI when needed
        void attachPresenter(T presenter);

    }

    // The Presenter interface
    // - call the view's methods to update the UI
    // - interact with the model to retrieve and save data
    interface Presenter {

        // the presenter will need to gather some initial data from the model when it is created
        void init();

    }
}
