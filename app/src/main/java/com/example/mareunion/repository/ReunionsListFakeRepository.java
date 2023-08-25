package com.example.mareunion.repository;

import com.example.mareunion.di.DI;
import com.example.mareunion.model.Reunion;
import com.example.mareunion.service.ReunionApiService;
import com.example.mareunion.ui.reunionlists.ReunionsListContract;
import com.example.mareunion.ui.utils.DateEasy;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;


/**
 * Model/Repository for the MVP MeetingsList
 * Note that, in a real app, this content MUST be dispatched to DAO, models, services, etc.
 */
public class ReunionsListFakeRepository implements ReunionsListContract.Model {

    /**
     * The start date filter
     * AVOID TO STORE THIS INSTANT HERE, IN A REAL APP, USE A DAO
     */
    private Instant mFilterStartDate;

    /**
     * The end date filter
     * AVOID TO STORE THIS INSTANT HERE, IN A REAL APP, USE A DAO
     */
    private Instant mFilterEndDate;

    /**
     * The place filter
     * AVOID TO STORE THIS STRING HERE, IN A REAL APP, USE A DAO
     */
    private String mFilterPlace;

    /**
     * The subject filter
     * AVOID TO STORE THIS STRING HERE, IN A REAL APP, USE A DAO
     */

    private String mFilterSubject;

    /**
     * The external service
     */
    private final ReunionApiService mReunionsApiService;

    /**
     * Constructor
     */
    public ReunionsListFakeRepository() {
        //Initialise le service
        mReunionsApiService = DI.getReunionApiService();
        //Initialise le filtre de départ a aujourd'hui
        mFilterStartDate = DateEasy.startOfDay(DateEasy.now());
        //Initialise le filtre de fin a dans un an
        mFilterEndDate = DateEasy.endOfDay(DateEasy.plusOneYear(DateEasy.now()));
        //Initialise le filtre de lieu a rien
        mFilterPlace = "";
        //Initialise le filtre de sujet a rien
        mFilterSubject = "";

    }
    @Override
    public List<Reunion> getAllReunions(){
        return mReunionsApiService.getReunions();
    }

    /**
     * Get the meetings list
     * @return the meetings list
     */
    @Override
    public List<Reunion> getFilteredAndSortedReunions() {
        // get the meetings list from the api service
        List<Reunion> meetings = mReunionsApiService.getReunions();

        // create a empty list of meetings and push the initial meetings list to the new list
        List<Reunion> filteredMeetings = new ArrayList<>(meetings);

        // filter the meetings list
        filterBySubject(filteredMeetings);
        // filter the meetings list
        filterByPlace(filteredMeetings);
        // filter the meetings list
        filterByTimeSpan(filteredMeetings);
        // sort the meetings list
        Collections.sort(filteredMeetings);

        // return the meetings list
        return filteredMeetings;
    }

    /**
     * Filter the meetings list by place
     * @param reunions the meetings list to be filtered
     */
    private void filterByPlace(List<Reunion> reunions) {
        // create an iterator on the meetings list
        Iterator<Reunion> meetingIterator = reunions.iterator();

        if(mFilterPlace != null && !mFilterPlace.equals("")) {
            // loop on the meetings list
            while (meetingIterator.hasNext()) {
                // if the next meeting place do not match the filter place
                if (!meetingIterator
                        .next()
                        .getLieu()
                        .getEndroit()
                        .toLowerCase()
                        .startsWith(mFilterPlace.toLowerCase())) {
                    // drop it from the list
                    meetingIterator.remove();
                }
            }
        }
    }
    private void filterBySubject(List<Reunion> reunions) {
        // create an iterator on the meetings list
        Iterator<Reunion> meetingIterator = reunions.iterator();

        if(mFilterSubject != null && !mFilterSubject.equals("")) {
            // loop on the meetings list
            while (meetingIterator.hasNext()) {
                // if the next meeting subject do not match the filter subject
                if (!meetingIterator
                        .next()
                        .getSujet()
                        .toLowerCase()
                        .startsWith(mFilterSubject.toLowerCase())) {
                    // drop it from the list
                    meetingIterator.remove();
                }
            }
        }
    }


    /**
     * Filter the meetings list by time span
     * @param reunions the meetings list to be filtered
     */
    private void filterByTimeSpan(List<Reunion> reunions) {
        // create an iterator on the meetings list
        Iterator<Reunion> meetingIterator = reunions.iterator();

        // loop on the meetings list
        while (meetingIterator.hasNext()) {
            // if the next meeting date is not included in the time span
            Reunion reunion = meetingIterator.next();
            if (
                    // the meeting date before the filter start date
                    (mFilterStartDate != null && reunion.getDate().compareTo(mFilterStartDate) < 0) ||
                    // the meeting date after the filter end date
                    (mFilterEndDate != null && reunion.getDate().compareTo(mFilterEndDate) > 0)
            ) {
                // drop it from the list
                meetingIterator.remove();

            }
        }

    }

    @Override
    public String getFilterSujet(){return mFilterSubject;}

    /**
     * Get the filter place
     * @return the filter place
     */
    @Override
    public String getFilterLieu() {
        return mFilterPlace;
    }

    /**
     * Get the filter start date
     * @return the filter start date
     */
    @Override
    public Instant getFilterStartDate() {
        return mFilterStartDate;
    }

    /**
     * Get the filter end date
     * @return the filter end date
     */
    @Override
    public Instant getFilterEndDate() {
        return mFilterEndDate;
    }

    /**
     * Drop a meeting
     * @param reunion the meeting to be dropped
     */
    @Override
    public void deleteReunion(Reunion reunion) {
        mReunionsApiService.deleteReunion(reunion);
    }

    /**
     * Set the start date filter
     * @param filterStartDate the start date filter
     */
    @Override
    public void setFilterStartDate(Instant filterStartDate) {
        mFilterStartDate = DateEasy.startOfDay(filterStartDate);
    }

    /**
     * Set the end date filter
     * @param filterEndDate the end date filter
     */
    @Override
    public void setFilterEndDate(Instant filterEndDate) {
        mFilterEndDate = DateEasy.endOfDay(filterEndDate);
    }

    /**
     * Set the place filter
     * @param filterPlace the place filter
     */
    @Override
    public void setFilterLieu(String filterPlace) {
        mFilterPlace = filterPlace;
    }

    @Override
    public void setFilterSujet(String filterSubject){
        mFilterSubject = filterSubject;
    }

}
