package com.aleperf.pathfinder.copernicana.model;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * MarsPhoto represent a photo taken by a Rover on Mars during the last available sol.
 * A sol is the duration of a solar day on Mars, the max sol for a Rover is the last
 * martian day on Mars since landing, the landing day is sol 0.
 */
@Entity(tableName = "mars_photos", primaryKeys = {"id", "rover_name"})
public class MarsPhoto {


    private int id;
    private int sol;

    @Embedded(prefix="camera_")
    @SerializedName("camera")
    private RoverCamera roverCamera;

    @SerializedName("img_src")
    private String imageUrl;

    @SerializedName("earth_date")
    private String earthDate;

    @Embedded(prefix="rover_")
    private Rover rover;
    private boolean isFavorite;

    public MarsPhoto(){}

    public int getId() {
        return id;
    }

    public int getSol() {
        return sol;
    }

    public RoverCamera getRoverCamera() {
        return roverCamera;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getEarthDate() {
        return earthDate;
    }

    public Rover getRover() {
        return rover;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public static class RoverCamera {

        private String name;
        @SerializedName("full_name")
        private String fullName;

        public String getName() {
            return name;
        }

        public String getFullName() {
            return fullName;
        }
    }

    public static class Rover {
        private String name;

        public String getName() {
            return name;
        }
    }


}
