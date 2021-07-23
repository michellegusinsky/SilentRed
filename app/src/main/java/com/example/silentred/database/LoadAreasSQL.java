package com.example.silentred.database;

import android.content.Context;

import com.example.silentred.model.Area;

import java.util.ArrayList;
import java.util.List;

public class LoadAreasSQL {

    private static ArrayList<Area> areas;
    private static Thread databaseThread = new Thread();


    public static ArrayList<Area> getAreasFromDatabase(Context context) {
        //databaseThread
        //databaseThread.start();
        AppDatabase appDb = AppDatabase.getAppDatabase(context);

        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                areas = new ArrayList<>(appDb.getAreaDao().getAllAreas());
            }
        });
        ArrayList<Area> areas = new ArrayList<>(appDb.getAreaDao().getAllAreas());
        if (areas != null){

        }
        return areas;
    //     Room.databaseBuilder();
    //      Room.inMemoryDatabaseBuilder();
    }

    private static void insertAreasToDatabase(Context context){
        AppDatabase appDb = AppDatabase.getAppDatabase(context);
        Area area = new Area("Haifa", "01:30");
        appDb.getAreaDao().insertArea(area);
    }

    private static void deleteAreaFromDatabase(Area area, Context context){
        AppDatabase appDb = AppDatabase.getAppDatabase(context);
        appDb.getAreaDao().insertArea(area);
    }
}
