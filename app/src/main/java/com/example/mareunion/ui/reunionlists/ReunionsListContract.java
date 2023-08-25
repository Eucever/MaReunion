package com.example.mareunion.ui.reunionlists;

import com.example.mareunion.core.MvpInterface;
import com.example.mareunion.model.Reunion;

import java.time.Instant;
import java.util.List;

public interface ReunionsListContract {

    interface Model extends MvpInterface.Model {
        // get all meetings
        List<Reunion> getAllReunions();

        // get the meetings list, regarding the current filters set
        List<Reunion> getFilteredAndSortedReunions();

        // get the filter place text
        String getFilterLieu();

        // get the filter subject text
        String getFilterSujet();

        // get the filter start date
        Instant getFilterStartDate();

        // get the filter end date
        Instant getFilterEndDate();

        // drop a meeting by its position
        void deleteReunion(Reunion reunion);

        // set the start date filter
        void setFilterStartDate(Instant filterStartDate);

        // set the end date filter
        void setFilterEndDate(Instant filterEndDate);

        // set the place filter
        void setFilterLieu(String filterPlace);

        void setFilterSujet(String filterSubject);

    }

    /**
     * View interface
     */
    interface View extends MvpInterface.View<Presenter> {

        // update the meetings list in the view
        void updateReunions(List<Reunion> reunions);

        // update meetings number
        void updateNbReunions(int nbFilteredReunions, int nbTotalReunion);

        // update the filters labels in the view
        void updateFilters(String filterSubject, String filterPlace, String filterStartDate, String filterEndDate);


        // trigger the meeting registration dialog
        void triggerReunionRegistrationDialog();

        // expand or collapse the filter card view
        void expandOrCollapseFilters();

        // set the error on the filter start date
        void setErrorFilterStartDate();

        // set the error on the filter end date
        void setErrorFilterEndDate();

        // trigger the date picker dialog
        void triggerDatePickerDialog(Instant date, boolean beginOrEnd);


    }

    /**
     * Presenter interface
     */
    interface Presenter extends MvpInterface.Presenter {

        // refresh the meetings list requested
        void onRefreshReunionsListRequested();

        // on filters have changed
        void onFiltersChanged(String filterSujet, String filterLieu, String filterStartDate, String filterEndDate);

        // drop a meeting request
        void dropReunionRequested(int position);

        // set the filter start date
        void setFilterStartDate(String filterStartDate);

        // set the filter start date manually
        void setFilterStartDateManual(String filterStartDate);

        // set the filter end date
        void setFilterEndDate(String filterEndDate);

        // set the filter end date manually
        void setFilterEndDateManual(String filterEndDate);

        // save the filter date
        void saveFilterDate(Instant date, boolean beginOrEnd);

        // save the filter place
        void saveFilterPlace(String filterPlace);

        void saveFilterSubject(String filterSubject);
    }
}
