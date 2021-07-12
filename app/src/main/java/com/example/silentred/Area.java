package com.example.silentred;


import java.sql.Time;

// The item class
public class Area {
    private String name;
    private Time time; // hh:mm:ss

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time.toString();
    }
    public void setTime(Time time) {
        this.time = time;
    }

    public Area(String name, Time time){
        this.name = name;
        this.time = time;
    }

    public Area() {
    }

    public int compare(Area other) {
        return  this.name.compareTo(other.name);
    }

    @Override
    public String toString() {
        return name;
    }
}
