package model;

/**
 * Apod represents the Astronomy Picture of the Day of the Nasa,
 * as served by the Apod API :https://api.nasa.gov/api.html#apod
 * The media type include video and images
 * Side note: the picture of day is visible at this website:
 * https://apod.nasa.gov/apod/astropix.html
 */

public class Apod {

    String copyright;
    String date;
    String explanation;
    String media_type;
    String hdurl;
    String title;
    String url;

    public Apod(String copyright, String date, String explanation,String media_type, String hdurl, String title, String url){
        this.copyright = copyright;
        this.date = date;
        this.explanation = explanation;
        this.media_type = media_type;
        this.hdurl = hdurl;
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

    public String getMedia_type() {
        return media_type;
    }

    public String getHdurl() {
        return hdurl;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }
}
