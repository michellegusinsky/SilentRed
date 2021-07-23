package com.example.silentred.database;

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
    List<Area> getAllAreas();

    @Query("SELECT * FROM area WHERE area_name IN (:areaNames)")
    List<Area> loadAllByIds(String[] areaNames);

    @Query("SELECT * FROM area WHERE time_get_shelter LIKE :time")
    Area findByTime(String time);

    @Insert
    void insertArea(Area area);

    @Delete
    void delete(Area area);

    @Update
    void updateArea(Area area);

}
