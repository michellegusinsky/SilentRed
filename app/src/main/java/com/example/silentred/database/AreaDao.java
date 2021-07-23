package com.example.silentred.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.silentred.model.Area;

import java.util.List;

@Dao
public interface AreaDao {
    @Query("SELECT * FROM area")
    LiveData<List<Area>> getAllAreas();

    @Query("SELECT * FROM area WHERE area_name IN (:areaNames)")
    LiveData<List<Area>> loadAllByNames(String[] areaNames);

    @Query("SELECT * FROM area WHERE time_get_shelter LIKE :time")
    LiveData<Area> findByTime(String time);

    @Query("SELECT * FROM area WHERE area_name LIKE :name")
    LiveData<Area> findByName(String name);

    @Insert
    void insertArea(Area area);

    @Delete
    void delete(Area area);

    @Query("DELETE FROM area")
    void deleteAll();

    @Update
    void updateArea(Area area);

}
