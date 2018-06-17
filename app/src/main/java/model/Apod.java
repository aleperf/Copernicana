package model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.Nullable;

import com.google.gson.annotations.SerializedName;

/**
 * Apod represents the Astronomy Picture of the Day of the Nasa,
 * as served by the Apod API :https://api.nasa.gov/api.html#apod
 * The mediaType field can be either video or image.
 * The copyright field is optional and can be null:
 * copyright is different from null only if the image isn't in the public domain.
 * Side note: the picture of day is visible at this website:
 * https://apod.nasa.gov/apod/astropix.html
 */
@Entity
public class Apod {

    @Nullable
    private String copyright;
    @PrimaryKey
    private String date; // date in format YYYY-MM-DD
    private String explanation;
    @SerializedName("media_Type")
    private String mediaType;
    @Nullable
    @SerializedName("hdurl")
    private String hdUrl;
    private String title;
    private String url;

    public Apod(@Nullable String copyright, String date, String explanation, String mediaType, @Nullable String hdUrl, String title, String url) {
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
