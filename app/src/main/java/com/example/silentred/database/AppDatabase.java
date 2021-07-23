package com.example.silentred.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.silentred.model.Area;
import com.example.silentred.xml.LoadAreasXML;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Contains the database holder and serves as the main access point for the underlying connection to relational data
@Database(entities = {Area.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static final String DB_NAME = "area_db";
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    private static AppDatabase INSTANCE;
    private static Context mContext;
    public abstract AreaDao getAreaDao(); //room implements this method

    public static synchronized AppDatabase getAppDatabase(Context context){
        if(INSTANCE == null){
            mContext = context;
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .addCallback(sRoomDatabaseCallback)
                    .build();
                    // allow queries on the main thread
                    // Don't do this on a real app! See PersistenceBasicSample for an example
                    //.allowMainThreadQueries()
                    //.build();
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                AreaDao dao = INSTANCE.getAreaDao();
                dao.deleteAll();
                ArrayList<Area> areas = LoadAreasXML.parseAreas(mContext);
                for (Area area : areas) {
                    dao.insertArea(area);
                }
            });
        }
    };

   // public void insertNewAreas(List<Area> areas){
   //     getAreaDao().insertAll(areas);
   // }

    public static void destroyInstance() {INSTANCE = null;}
}
