package com.example.silentred;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class TimeAreasActivity extends AppCompatActivity implements LifecycleOwner{//} , AreasAndTimesAdapter.AreaListener{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.areas_and_times_recycle_layout);
            Log.i("silentRed", "TimeAreasActivity onCreate");

            // Lookup the recyclerview in activity layout
            RecyclerView rvAreasAndTimes = findViewById(R.id.recycler_view);

            // Create adapter passing in the sample user data
            AreasAndTimesAdapter adapter = new AreasAndTimesAdapter(this);
            // Attach the adapter to the recyclerview to populate items
            rvAreasAndTimes.setAdapter(adapter);
            // Set layout manager to position the items
            //or GreedLayout instead of LinearLayout
            rvAreasAndTimes.setLayoutManager(new LinearLayoutManager(this.getBaseContext()));
            Log.i("silentRed", "TimeAreasActivity set adapter");
        } catch (Exception e) {
            Log.e("silentRed", "TimeAreasActivity onCreate Exception: " + e.getMessage());
        }
    }

   /* @Override
    public void onClickArea() {

    }*/
}

