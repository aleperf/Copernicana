package model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Apod represents the Astronomy Picture of the Day of the Nasa,
 * as served by the Apod API :https://api.nasa.gov/api.html#apod
 * The media type include video and images
 * Side note: the picture of day is visible at this website:
 * https://apod.nasa.gov/apod/astropix.html
 */
@Entity
public class Apod {

    String copyright;
    @PrimaryKey
    String date;
    String explanation;
    @SerializedName("media_Type")
    String mediaType;
    @SerializedName("hdurl")
    String hdUrl;
    String title;
    String url;

    public Apod(String copyright, String date, String explanation,String mediaType, String hdUrl, String title, String url){
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
}
