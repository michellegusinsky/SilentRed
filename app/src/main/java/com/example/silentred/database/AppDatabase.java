package com.example.silentred.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.silentred.model.Area;

// Contains the database holder and serves as the main access point for the underlying connection to relational data
@Database(entities = {Area.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "area_db";
    private static AppDatabase INSTANCE;

    public abstract AreaDao getAreaDao(); //room implements this method

    public static synchronized AppDatabase getAppDatabase(Context context){
        if(INSTANCE == null){
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
                    // allow queries on the main thread
                    // Don't do this on a real app! See PersistenceBasicSample for an example
                    //.allowMainThreadQueries()
                    //.build();
        }
        return INSTANCE;
    }

   // public void insertNewAreas(List<Area> areas){
   //     getAreaDao().insertAll(areas);
   // }

    public static void destroyInstance() {INSTANCE = null;}
}
