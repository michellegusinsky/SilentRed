package com.example.silentred.adapters;

import android.content.Context;
//import android.graphics.Color;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.silentred.viewModels.AreasAndTimesViewModel;
import com.example.silentred.R;
import com.example.silentred.activities.TimeAreasActivity;
import com.example.silentred.model.Area;

//import java.util.ArrayList;

public class AreasAndTimesAdapter extends ListAdapter<Area,AreasAndTimesAdapter.MyViewHolder> {//RecyclerView.Adapter<AreasAndTimesAdapter.MyViewHolder>{

    // region members
   // private ArrayList<MyViewHolder> viewHolders;
   // private int row_index = -1;
    protected AreasAndTimesViewModel areasAndTimesViewModel;
    // private AreaListener clickListener;
    // endregion

  /*  public interface AreaListener {
         void onClickArea();
    }*/

    // region Constructor
    public AreasAndTimesAdapter(TimeAreasActivity activity, @NonNull DiffUtil.ItemCallback<Area> diffCallback){
        super(diffCallback);
        try {
            //noinspection deprecation
            areasAndTimesViewModel = ViewModelProviders.of(activity).get(AreasAndTimesViewModel.class);
            // another version of the above line
            // areasAndTimesViewModel = new ViewModelProvider(activity,
           //         ViewModelProvider.AndroidViewModelFactory.getInstance(activity.getApplication())).get(AreasAndTimesViewModel.class);

          //  clickListener = activity;

            // updates when areas change
            areasAndTimesViewModel.getAreaItems().observe(activity, areas -> {
                // Update the cached copy of the words in the adapter.
                this.submitList(areas);
            });

            Log.i("silentRed", "AreasAndTimesAdapter Constructor");
        } catch (Exception e){
            Log.e("silentRed", "AreasAndTimesAdapter Constructor Exception: " + e.getMessage());
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
    //    private View itemView;
    //    private LinearLayout linearLayoutView;
      //  private int backgroundColor;
      //  private int position;
        // endregion

        // region Constructor
        //Constructor: get all widgets
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            try {
            //    this.itemView = itemView;
            //    linearLayoutView = itemView.findViewById(R.id.linear_layout);
                nameTextView = itemView.findViewById(R.id.textView_areaName);
                timeTextView = itemView.findViewById(R.id.textView_time);
              //  backgroundColor = R.color.teal_200;
            } catch (Exception e) {
                Log.e("silentRed", "AreasAndTimesAdapter.MyViewHolder Constructor Exception: " + e.getMessage());
            }
        }
        // endregion

        public void bindData(Area area) {
            try {
                // Set item views based on your views and data model
                nameTextView.setText(area.getName());
                timeTextView.setText(area.getTime());
             //   position =getAdapterPosition();
                // defined listeners to view widget
                //setOnLongClickListener(area);

                // defined listeners to view widget
                //setOnClickListener(area, this);

            } catch (Exception e) {
                Log.e("silentRed", "AreasAndTimesAdapter.MyViewHolder bindData Exception: " + e.getMessage());
            }
        }

        // region Events register + handle
    /*    private void setOnLongClickListener(Area area){
            try {
                itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
                        if (model.getAreaItems().getValue() == null) return false;
                        if (model.getAreaItems().getValue().contains(area)) {
                            model.getAreaItems().getValue().remove(area);
                            notifyDataSetChanged();
                            return true;
                        }
                        return false;
                    }
                });
            }catch (Exception e) {
                Log.e("silentRed", "AreasAndTimesAdapter.MyViewHolder setOnLongClickListener Exception: " + e.getMessage());
            }
        }*/

        // change the color of the row to white when clicked
       /* private void setOnClickListener(Area area, MyViewHolder owner){
            try {
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        row_index=position;
                        notifyDataSetChanged();
                        clickListener.onClickArea(); }
                });
                if(row_index==position){
                    linearLayoutView.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
                    timeTextView.setTextColor(Color.parseColor("#FF000000"));
                    model.setSelectedArea(position);
                }
                else
                {
                    linearLayoutView.setBackgroundColor(Color.parseColor("#FF2196F3"));
                    timeTextView.setTextColor(Color.parseColor("#FFFFFFFF"));
                }

            }catch (Exception e) {
                Log.e("silentRed", "AreasAndTimesAdapter.MyViewHolder setOnClickListener Exception: " + e.getMessage());
            }
        }
        // endregion*/
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
           // MyViewHolder viewHolder = new MyViewHolder(areaView);
            //viewHolders.add(viewHolder);
            return new MyViewHolder(areaView);
        } catch (Exception e){
            Log.e("silentRed", "AreasAndTimesAdapter onCreateViewHolder Exception: " + e.getMessage());
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
            Log.e("silentRed", "AreasAndTimesAdapter onBindViewHolder Exception: " + e.getMessage());
        }
    }


    // number of items in dataset
    @Override
    public int getItemCount() {
        try {
            if (areasAndTimesViewModel.getAreaItems().getValue() == null) return 0;
            return areasAndTimesViewModel.getAreaItems().getValue().size();

        }catch (Exception e){
            Log.e("silentRed", "AreasAndTimesAdapter getItemCount Exception: " + e.getMessage());
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
