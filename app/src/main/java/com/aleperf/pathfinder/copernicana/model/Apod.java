package com.aleperf.pathfinder.copernicana.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.graphics.Movie;
import android.os.Parcel;
import android.os.Parcelable;
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
public class Apod implements Parcelable{
    public static final String MEDIA_TYPE_VIDEO = "video";
    public static final String MEDIA_TYPE_IMAGE = "image";

    @Nullable
    String copyright;
    @PrimaryKey @NonNull
    String date;
    String explanation;
    @SerializedName("media_type")
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

    //Constructor used by Parcelable to deserialize data
    private Apod(Parcel in){
        copyright = in.readString();
        date = in.readString();
        explanation = in.readString();
        mediaType = in.readString();
        hdUrl = in.readString();
        title = in.readString();
        url = in.readString();
        isFavorite = in.readInt();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {

        parcel.writeString(copyright);
        parcel.writeString(date);
        parcel.writeString(explanation);
        parcel.writeString(mediaType);
        parcel.writeString(hdUrl);
        parcel.writeString(title);
        parcel.writeString(url);
        parcel.writeInt(isFavorite);

    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public Apod createFromParcel(Parcel in) {
            return new Apod(in);
        }

        public Apod[] newArray(int size) {
            return new Apod[size];
        }
    };
}
