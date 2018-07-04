package com.aleperf.pathfinder.copernicana.model;

/**
 * Represents an astronaut in space now
 */

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;

@Entity(tableName = "astronauts")
public class Astronaut {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @Expose
    private String name;
    @Expose
    private String craft;


    public Astronaut(int id, String name, String craft){
        this.name = name;
        this.craft = craft;
    }

    public String getName() {
        return name;
    }

    public String getCraft() {
        return craft;
    }
}
