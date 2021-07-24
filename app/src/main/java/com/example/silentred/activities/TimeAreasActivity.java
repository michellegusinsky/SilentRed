package com.example.silentred.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.silentred.adapters.AreasAndTimesAdapter;
import com.example.silentred.R;

import static com.example.silentred.common.Constants.APP_TAG;

public class TimeAreasActivity extends AppCompatActivity implements LifecycleOwner{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.areas_and_times_recycle_layout);
            Log.i(APP_TAG, "TimeAreasActivity onCreate");

            // Lookup the recyclerview in activity layout
            RecyclerView rvAreasAndTimes = findViewById(R.id.recycler_view);

            // Create adapter passing in the sample user data
            AreasAndTimesAdapter adapter = new AreasAndTimesAdapter(this, new AreasAndTimesAdapter.AreaDiff());
            // Attach the adapter to the recyclerview to populate items
            rvAreasAndTimes.setAdapter(adapter);
            // Set layout manager to position the items
            rvAreasAndTimes.setLayoutManager(new LinearLayoutManager(this.getBaseContext()));
            Log.i(APP_TAG, "TimeAreasActivity set adapter");
        } catch (Exception e) {
            Log.e(APP_TAG, "TimeAreasActivity onCreate Exception: " + e.getMessage());
        }
    }
}

