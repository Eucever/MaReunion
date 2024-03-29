package com.example.mareunion.ui.reunionlists;

import static com.google.common.base.Preconditions.checkNotNull;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.mareunion.model.Reunion;
import com.example.mareunion.ui.utils.DateEasy;

import java.time.Instant;
import java.util.List;

public class ReunionsListPresenter implements ReunionsListContract.Presenter {
    private static final String TAG = "ReunionsListPresenter";

    private final ReunionsListContract.View mView;

    private final ReunionsListContract.Model mModel;

    private List<Reunion> mFilteredAndSortedMeetings;

    public ReunionsListPresenter(@NonNull ReunionsListContract.View view,
                                 @NonNull ReunionsListContract.Model model) {
        mView = checkNotNull(view);
        mModel = checkNotNull(model);
        // important : immediately attach the presenter in the view
        mView.attachPresenter(this);
    }

    @Override
    public void init() {
        onRefreshReunionsListRequested();
    }

    @Override
    public void onRefreshReunionsListRequested() {
        Log.d("OnRefresh","List rafraichissements");
        // get the meetings list, either filtered or not
        List<Reunion> reunions = mModel.getFilteredAndSortedReunions();
        // buffer the list, in order to ease the deletion of a meeting
        mFilteredAndSortedMeetings = reunions;

        mView.updateNbReunions(mFilteredAndSortedMeetings.size(), mModel.getAllReunions().size());
        // update the view with the fresh meetings list
        mView.updateReunions(reunions);
        // update the view with the up to date filters
        mView.updateFilters(
                mModel.getFilterSujet(),
                mModel.getFilterLieu(),
                DateEasy.localeDateStringFromInstant(mModel.getFilterStartDate()),
                DateEasy.localeDateStringFromInstant(mModel.getFilterEndDate())
        );
    }

    @Override
    public void dropReunionRequested(int position) {
        // drop the meeting
        mModel.deleteReunion(mFilteredAndSortedMeetings.get(position));
        // refresh the meetings list
        onRefreshReunionsListRequested();
    }

    @Override
    public void onFiltersChanged(String filterSujet, String filterLieu, String filterStartDate, String filterEndDate){
        // set error to false
        boolean isError = false;

        // is the start date is empty
        if (filterStartDate.isEmpty()) {
            // set the start date to null
            mModel.setFilterStartDate(null);
        } else {
            // try to parse the start date
            Instant tmp = DateEasy.parseDateStringToInstant(filterStartDate);
            if (tmp == null) {
                // if the start date is not valid, set the error flag to true
                isError = true;
                mView.setErrorFilterStartDate();
            } else {
                // if the start date is valid, set the filter
                mModel.setFilterStartDate(tmp);
            }
        }

        // if the end date is empty
        if (filterEndDate.isEmpty()) {
            // set the end date to null
            mModel.setFilterEndDate(null);
        } else {
            // try to parse the end date
            Instant tmp = DateEasy.parseDateStringToInstant(filterEndDate);
            // if the end date is not valid, set the error flag to true
            if (tmp == null) {
                // if the end date is not valid, set the error flag to true
                isError = true;
                mView.setErrorFilterEndDate();
            } else {
                // if the end date is valid, set the filter
                mModel.setFilterEndDate(tmp);
            }
        }
        // if there is no error
        mModel.setFilterLieu(filterLieu);
        mModel.setFilterSujet(filterSujet);
        // if no error
        if (!isError) {
            // collapse filter
            mView.expandOrCollapseFilters();
            // refresh the meetings list
            onRefreshReunionsListRequested();
        }

    }

    @Override
    public void setFilterStartDate(String filterStartDate) {
        Log.d("COUCOU", filterStartDate.toString());

        if (filterStartDate.isEmpty()) {
            mModel.setFilterStartDate(null);
            mView.triggerDatePickerDialog(mModel.getFilterStartDate(), true);
        } else {
            Instant tmp = DateEasy.parseDateStringToInstant(filterStartDate);
            if (tmp != null) {
                mModel.setFilterStartDate(tmp);
                mView.triggerDatePickerDialog(mModel.getFilterStartDate(), true);
            }
        }

    }

    @Override
    public void setFilterStartDateManual(String filterStartDate) {
        if (filterStartDate.isEmpty()) {
            mModel.setFilterStartDate(null);
            onRefreshReunionsListRequested();
        } else {
            Instant tmp = DateEasy.parseDateStringToInstant(filterStartDate);
            if (tmp != null) {
                mModel.setFilterStartDate(tmp);
                onRefreshReunionsListRequested();
            } else {
                mView.setErrorFilterStartDate();
            }
        }

    }

    @Override
    public void setFilterEndDate(String filterEndDate) {
        if (filterEndDate.isEmpty()) {
            mModel.setFilterEndDate(null);
            mView.triggerDatePickerDialog(mModel.getFilterEndDate(), false);
        } else {
            Instant tmp = DateEasy.parseDateStringToInstant(filterEndDate);
            if (tmp != null) {
                mModel.setFilterEndDate(tmp);
                mView.triggerDatePickerDialog(mModel.getFilterEndDate(), false);
            }
        }

    }

    @Override
    public void setFilterEndDateManual(String filterEndDate) {
        if (filterEndDate.isEmpty()) {
            mModel.setFilterEndDate(null);
            onRefreshReunionsListRequested();
        } else {
            Instant tmp = DateEasy.parseDateStringToInstant(filterEndDate);
            if (tmp != null) {
                mModel.setFilterEndDate(tmp);
                onRefreshReunionsListRequested();
            } else {
                mView.setErrorFilterEndDate();
            }
        }

    }

    @Override
    public void saveFilterDate(Instant date, boolean beginOrEnd) {
        if (beginOrEnd) {
            mModel.setFilterStartDate(date);
        } else {
            mModel.setFilterEndDate(date);
        }
        onRefreshReunionsListRequested();
    }

    /**
     * Save filter on place
     * @param filterPlace the place filter
     */
    @Override
    public void saveFilterPlace(String filterPlace) {
        mModel.setFilterLieu(filterPlace);
        onRefreshReunionsListRequested();
    }
    @Override
    public void saveFilterSubject(String filterSubject) {
        mModel.setFilterSujet(filterSubject);
        onRefreshReunionsListRequested();
    }

    @Override
    public void resetAllfilters(){
        mModel.resetFilters();
        onRefreshReunionsListRequested();
    }
}
