package com.aleperf.pathfinder.copernicana.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Apod represents the Astronomy Picture of the Day of the Nasa,
 * as served by the Apod API :https://api.nasa.gov/api.html#apod
 * The mediaType field can be either video or image.
 * The copyright field can be null, it is different from null only if the image isn't in the public domain.
 * Side note: the picture of day is visible at this website:
 * https://apod.nasa.gov/apod/astropix.html
 */
@Entity(tableName = "apod")
public class Apod {
    @Nullable
    String copyright;
    @PrimaryKey @NonNull
    String date;
    String explanation;
    @SerializedName("media_Type")
    String mediaType;
    @Nullable
    @SerializedName("hdurl")
    String hdUrl;
    String title;
    String url;
    int isFavorite = 0;

    public Apod(@Nullable String copyright, String date, String explanation,String mediaType, String hdUrl, String title, String url){
        this.copyright = copyright;
        this.date = date;
        this.explanation = explanation;
        this.mediaType = mediaType;
        this.hdUrl = hdUrl;
        this.title = title;
        this.url = url;
    }

    public String getCopyright() {
        return copyright;
    }

    public String getDate() {
        return date;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getMediaType() {
        return mediaType;
    }

    public String getHdUrl() {
        return hdUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public int getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(int isFavorite){
        this.isFavorite = isFavorite;
    }
}
