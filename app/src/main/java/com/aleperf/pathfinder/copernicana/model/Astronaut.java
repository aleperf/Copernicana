package com.aleperf.pathfinder.copernicana.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * Astronaut represents an astronaut in space now
 */

@Entity(tableName = "astronaut")
public class Astronaut implements Parcelable {

    @PrimaryKey
    @Expose
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
    private String wikipedia;
    @Expose
    private String notes;


    public Astronaut(int id, String name, String craft, String country,
                     String agency, String photo, int evaHours, int evaMinutes,
                     String wikipedia, String notes ){
        this.id = id;
        this.name = name;
        this.craft = craft;
        this.country = country;
        this.agency = agency;
        this.photo = photo;
        this.evaHours = evaHours;
        this.evaMinutes = evaMinutes;
        this.wikipedia = wikipedia;
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

    public String getWikipedia() {
        return wikipedia;
    }

    public String getNotes() {
        return notes;
    }

    private Astronaut(Parcel in){
        id = in.readInt();
        name = in.readString();
        craft = in.readString();
        country = in.readString();
        agency = in.readString();
        photo = in.readString();
        evaHours = in.readInt();
        evaMinutes = in.readInt();
        wikipedia = in.readString();
        notes = in.readString();
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public Astronaut createFromParcel(Parcel in) {
            return new Astronaut(in);
        }

        public Astronaut[] newArray(int size) {
            return new Astronaut[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(craft);
        dest.writeString(country);
        dest.writeString(agency);
        dest.writeString(photo);
        dest.writeInt(evaHours);
        dest.writeInt(evaMinutes);
        dest.writeString(wikipedia);
        dest.writeString(notes);

    }
}
