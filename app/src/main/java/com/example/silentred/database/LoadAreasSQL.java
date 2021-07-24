package com.example.silentred.database;

import android.app.Application;
import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.silentred.model.Area;

import java.util.ArrayList;
import java.util.List;

public class LoadAreasSQL {
    private AreaDao mAreaDao;
    private LiveData<List<Area>> mAllAreas;

    public LoadAreasSQL(Application application) {
        AppDatabase db = AppDatabase.getAppDatabase(application); // get Instance of database
        mAreaDao = db.getAreaDao();
        mAllAreas = mAreaDao.getAllAreas();
    }

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    public LiveData<List<Area>> getAllAreas() {
        return mAllAreas;
    }

    public ArrayList<String> getAllAreasNames(){
        ArrayList<Area> areas = new ArrayList<>(mAllAreas.getValue());
        ArrayList<String> areasNames = new ArrayList<>(areas.size());
        for (Area area : areas){
            areasNames.add(area.name);
        }
        return areasNames;
    }

    // region We didn't use it
    // You must call this on a non-UI thread or your app will throw an exception. Room ensures
    // that you're not doing any long running operations on the main thread, blocking the UI.
    /*void insert(Area area) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            mAreaDao.insertArea(area);
        });
    }
    private static ArrayList<Area> areas;

    private static void insertAreasToDatabase(Context context){
        AppDatabase appDb = AppDatabase.getAppDatabase(context);
        Area area = new Area("Haifa", "01:30");
        appDb.getAreaDao().insertArea(area);
    }

    private static void deleteAreaFromDatabase(Area area, Context context){
        AppDatabase appDb = AppDatabase.getAppDatabase(context);
        appDb.getAreaDao().insertArea(area);
    }
    */
    // endregion
}
