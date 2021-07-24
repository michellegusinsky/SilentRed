package com.example.silentred.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.silentred.viewModels.AreasAndTimesViewModel;
import com.example.silentred.R;
import com.example.silentred.activities.TimeAreasActivity;
import com.example.silentred.model.Area;

import static com.example.silentred.common.Constants.APP_TAG;


public class AreasAndTimesAdapter extends ListAdapter<Area,AreasAndTimesAdapter.MyViewHolder> {

    // region members
    protected AreasAndTimesViewModel areasAndTimesViewModel;
    private static final String class_tag = AreasAndTimesAdapter.class.getName();
    // endregion

    // region Constructor
    public AreasAndTimesAdapter(TimeAreasActivity activity, @NonNull DiffUtil.ItemCallback<Area> diffCallback){
        super(diffCallback);
        try {
            //noinspection deprecation
            areasAndTimesViewModel = ViewModelProviders.of(activity).get(AreasAndTimesViewModel.class);

            // updates when areas change
            areasAndTimesViewModel.getAreaItems().observe(activity, areas -> {
                // Update the cached copy of the words in the adapter.
                this.submitList(areas);
            });

            Log.i(APP_TAG, class_tag +" Constructor");
        } catch (Exception e){
            Log.e(APP_TAG, class_tag +" Constructor Exception: " + e.getMessage());
        }
    }
    // endregion

    //region inner class - ViewHolder
    // Provide a direct reference to each of the views within a data item
    // Used to cache the views within the item layout for fast access
    // "ViewHolder" describes and provides access to all the views within each item row
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        // region members
        // members: all views of item row
        private TextView nameTextView;
        private TextView timeTextView;
        private static final String inner_class_tag = AreasAndTimesAdapter.MyViewHolder.class.getName();
        // endregion

        // region Constructor
        //Constructor: get all widgets
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            try {
                nameTextView = itemView.findViewById(R.id.textView_areaName);
                timeTextView = itemView.findViewById(R.id.textView_time);
            } catch (Exception e) {
                Log.e(APP_TAG, inner_class_tag+" Constructor Exception: " + e.getMessage());
            }
        }
        // endregion

        public void bindData(Area area) {
            try {
                // Set item views based on your views and data model
                nameTextView.setText(area.getName());
                timeTextView.setText(area.getTime());

            } catch (Exception e) {
                Log.e(APP_TAG, inner_class_tag+" bindData Exception: " + e.getMessage());
            }
        }
    }
    // endregion

    // region Adapter override methods
    // create new item widget view
    // to inflate the item layout and create the holder
    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            Context context = parent.getContext();
            LayoutInflater inflater = LayoutInflater.from(context);

            // Inflate the custom layout
            View areaView = inflater.inflate(R.layout.time_according_to_area_row, parent, false);

            // Return a new holder instance
            return new MyViewHolder(areaView);
        } catch (Exception e){
            Log.e(APP_TAG, class_tag+" onCreateViewHolder Exception: " + e.getMessage());
        }
        return null;
    }

    // called when scroll down
    // to fill the widget view cell
    // to set the view attributes based on the data
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            if (areasAndTimesViewModel.getAreaItems().getValue() == null) return;

            // Get the data model based on position
            Area area = areasAndTimesViewModel.getAreaItems().getValue().get(position);

            holder.bindData(area);
        } catch (Exception e){
            Log.e(APP_TAG, class_tag+" onBindViewHolder Exception: " + e.getMessage());
        }
    }


    // number of items in dataset
    @Override
    public int getItemCount() {
        try {
            if (areasAndTimesViewModel.getAreaItems().getValue() == null) return 0;
            return areasAndTimesViewModel.getAreaItems().getValue().size();

        }catch (Exception e){
            Log.e(APP_TAG, class_tag+" getItemCount Exception: " + e.getMessage());
        }
        return 0;
    }
    //endregion

    public static class AreaDiff extends DiffUtil.ItemCallback<Area> {

        @Override
        public boolean areItemsTheSame(@NonNull Area oldItem, @NonNull Area newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Area oldItem, @NonNull Area newItem) {
            return oldItem.getName().equals(newItem.getName());
        }
    }
}
