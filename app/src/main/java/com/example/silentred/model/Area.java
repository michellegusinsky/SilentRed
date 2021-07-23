package com.example.silentred.model;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

// The item class
// Represents a table within the database
@Entity (tableName = "area")
public class Area {

    // primary key of the entity.
    // autoGenerate — if set to true, then SQLite will be generating a unique id for the column
    @PrimaryKey(autoGenerate = true)
    public int areaNumber;

    // allows specifying custom information about column
    @ColumnInfo(name = "area_name")
    public String name;

    @ColumnInfo(name = "time_get_shelter")
    private String time; // hh:mm:ss

    public String getName() {
        return name;
    }
    public String getTime() {
        return time;
    }

    @Ignore
    public void setName(String name) {
        this.name = name;
    }
    @Ignore
    public void setTime(String time) {
        this.time = time;
    }

    public Area(int areaNumber, String name, String time){
        this.areaNumber = areaNumber;
        this.name = name;
        this.time = time;
    }

    @Ignore
    public Area(String name, String time){
        this.name = name;
        this.time = time;
    }

    @Ignore
    public Area() {}

    @Ignore
    public int compare(Area other) {
        return  this.name.compareTo(other.name);
    }

    @Ignore
    @Override
    public String toString() {
        return name;
    }
}
