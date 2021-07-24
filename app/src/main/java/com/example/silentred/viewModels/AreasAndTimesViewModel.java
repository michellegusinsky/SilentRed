package com.example.silentred.viewModels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.silentred.database.LoadAreasSQL;
import com.example.silentred.model.Area;

import java.util.List;

import static com.example.silentred.common.Constants.APP_TAG;

public class AreasAndTimesViewModel extends AndroidViewModel {

    // region members
    private LiveData<List<Area>> areasLiveData;
    private Application app;
    private static final String class_tag = AreasAndTimesViewModel.class.getName();
    // endregion

    // region constructor
    public AreasAndTimesViewModel(@NonNull Application application) {
        super(application);
        try {
            app = application;
            initAreasList();
        }catch (Exception e){
            Log.e(APP_TAG, class_tag+" Constructor Exception: " + e.getMessage());
        }
    }
    // endregion

    // region getters
    public LiveData<List<Area>> getAreaItems(){
        try {
            if (areasLiveData == null) {
                initAreasList();
            }
            return areasLiveData;
        }catch (Exception e){
            Log.e(APP_TAG, class_tag+" getAreaItems Exception: " + e.getMessage());
        }
        return null;
    }
    // endregion

    // region init methods
    private void initAreasList(){
        try {
            areasLiveData = new MutableLiveData<>();
            LoadAreasSQL repository = new LoadAreasSQL(app);  // the class that handles the sql access
            areasLiveData = repository.getAllAreas();
            // ArrayList<Area> areaItems = LoadAreasXML.parseAreas(context); // for using xml
        }catch (Exception e){
            Log.e(APP_TAG, class_tag+" initAreasList Exception: " + e.getMessage());
        }
    }
    // endregion

}
