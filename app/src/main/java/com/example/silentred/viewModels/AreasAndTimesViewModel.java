package com.example.silentred.viewModels;

//import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.silentred.database.LoadAreasSQL;
import com.example.silentred.model.Area;
import com.example.silentred.xml.LoadAreasXML;

import java.util.ArrayList;
import java.util.List;

public class AreasAndTimesViewModel extends AndroidViewModel {

    // region members
    private LoadAreasSQL repository;
    private LiveData<List<Area>> areasLiveData;
    private Application app;
 //   private MutableLiveData<Integer> itemSelectedLiveData;

    // endregion

    // region constructor
    public AreasAndTimesViewModel(@NonNull Application application) {
        super(application);
        try {
            app = application;
            initAreasList();
          //  initSelectedArea();
        }catch (Exception e){
            Log.e("silentRed", "AreasAndTimesViewModel Constructor Exception: " + e.getMessage());
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
            Log.e("silentRed", "AreasAndTimesViewModel getAreaItems Exception: " + e.getMessage());
        }
        return null;
    }

 /*   public LiveData<Area> getSelectedArea(){
       try {
           if (itemSelectedLiveData == null) {
               initSelectedArea();
               return null;
           }
           if (areaItems == null) {
               initAreasList();
           }
           if (areaItems.size() <= itemSelectedLiveData.getValue() || itemSelectedLiveData.getValue() < 0 ) {
               return null;
           }
           MutableLiveData<Area> areaLiveData = new MutableLiveData<>();
           areaLiveData.setValue(areaItems.get(itemSelectedLiveData.getValue()));
           return areaLiveData;
       }catch (Exception e){
           Log.e("silentRed", "AreasAndTimesViewModel getSelectedArea Exception: " + e.getMessage());
       }
       return null;
    }*/
    // endregion

    // region setters
    /*public void setSelectedArea(int i){
        try {
            if (itemSelectedLiveData == null) {
                itemSelectedLiveData = new MutableLiveData<>();
            }
            itemSelectedLiveData.setValue(i);
        }catch (Exception e){
            Log.e("silentRed", "AreasAndTimesViewModel setSelectedArea Exception: " + e.getMessage());
        }
    }*/
    // endregion

    // region init methods
    private void initAreasList(){
        try {
          //  areasLiveData = new MutableLiveData<>();
            Context context = getApplication().getApplicationContext();
            repository = new LoadAreasSQL(app);
            areasLiveData = repository.getAllAreas();
            // ArrayList<Area> areaItems = LoadAreasXML.parseAreas(context);
        //    areasLiveData.setValue(areaItems);
        }catch (Exception e){
            Log.e("silentRed", "AreasAndTimesViewModel initAreasList Exception: " + e.getMessage());
        }
    }


  /*  private void initSelectedArea() {
        try {
            itemSelectedLiveData = new MutableLiveData<>();
            itemSelectedLiveData.setValue(-1);
        }catch (Exception e){
            Log.e("silentRed", "AreasAndTimesViewModel initSelectedArea Exception: " + e.getMessage());
        }
    }*/
    // endregion

}
