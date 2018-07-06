package com.aleperf.pathfinder.copernicana.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Astronaut represents an astronaut in space now
 */

@Entity(tableName = "astronaut")
public class Astronaut {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @Expose
    private String name;
    @Expose
    private String craft;
    @Expose
    private String country;
    @Expose
    private String agency;
    @Expose
    private String photo;
    @Expose
    @SerializedName("eva_hours")
    private int evaHours;
    @Expose
    @SerializedName("eva_minutes")
    private int evaMinutes;
    @Expose
    private String twitter;
    @Expose
    private String notes;


    public Astronaut(int id, String name, String craft, String country,
                     String agency, String photo, int evaHours, int evaMinutes,
                     String twitter, String notes ){
        this.id = id;
        this.name = name;
        this.craft = craft;
        this.country = country;
        this.agency = agency;
        this.photo = photo;
        this.evaHours = evaHours;
        this.evaMinutes = evaMinutes;
        this.twitter = twitter;
        this.notes = notes;
    }

    public int getId(){
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCraft() {
        return craft;
    }

    public String getCountry() {
        return country;
    }

    public String getAgency() {
        return agency;
    }

    public String getPhoto() {
        return photo;
    }

    public int getEvaHours() {
        return evaHours;
    }

    public int getEvaMinutes() {
        return evaMinutes;
    }

    public String getTwitter() {
        return twitter;
    }

    public String getNotes() {
        return notes;
    }
}
