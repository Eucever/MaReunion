package com.example.mareunion.ui.reunionlists;

import static com.google.common.base.Preconditions.checkNotNull;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mareunion.R;
import com.example.mareunion.model.Reunion;

import java.util.List;

public class ReunionsListAdapter extends RecyclerView.Adapter<ReunionsListViewholder> {
    public interface DropClickListener {
        void onClick(View v, int position);
    }

    private List<Reunion> mReunions;

    private final DropClickListener mOnDropClickListener;

    public ReunionsListAdapter(List<Reunion> reunions, DropClickListener onDropClickListener) {
        mOnDropClickListener = onDropClickListener;
        setReunions(reunions);
    }

    /**
     * Create the view holder, that will be used to display a meeting
     *
     * @param parent the parent view
     * @param viewType the view type
     * @return
     */
    @NonNull
    @Override
    public ReunionsListViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // context of the parent view
        Context context = parent.getContext();
        // inflate the view holder layout
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.fragment_meetings, parent, false);
        // return the view holder
        return new ReunionsListViewholder(view, mOnDropClickListener);
    }

    /**
     * Update the view holder with the meeting to be displayed
     *
     * @param holder the view holder
     * @param position the position in order to get the corresponding meeting to be displayed
     */
    @Override
    public void onBindViewHolder(@NonNull ReunionsListViewholder holder, int position) {
        holder.setMeeting(mReunions.get(position));
    }

    /**
     * Get the number of meetings to be displayed
     *
     * @return the number of meetings to be displayed
     */
    @Override
    public int getItemCount() {
        return mReunions.size();
    }

    @SuppressLint("NotifyDataSetChanged")
    public void updateReunions(List<Reunion> reunions) {
        // set the list of meeting to be displayed
        setReunions(reunions);
        // notify the data set changed globally
        notifyDataSetChanged();
    }

    private void setReunions(List<Reunion> reunions) {
        mReunions = checkNotNull(reunions);
    }
}
