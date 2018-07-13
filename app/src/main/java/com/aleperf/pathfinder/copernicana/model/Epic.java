package com.aleperf.pathfinder.copernicana.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

/**
 * Epic represents a picture by the DSCOVR's Earth Polychromatic Imaging Camera (EPIC) instrument
 * as provided by the EPIC API.
 * More info about the API at https://epic.gsfc.nasa.gov/about/api
 */
@Entity(tableName = "epic")
public class Epic {

    @PrimaryKey
    long identifier;
    String caption;
    String image;
    @SerializedName("centroid_coordinates")
    Coord2D centroid;
    @SerializedName("dscovr_j2000_position")
    Coord3D epicPosition;
    @SerializedName("lunar_j2000_position")
    Coord3D moonPosition;
    @SerializedName("sun_j2000_position")
    Coord3D sunPosition;
    @SerializedName("attitude_quaternions")
    AttitudeQuaternions attitudeQuaternions;
    String date;
    boolean isNatural;
    boolean isFavorite;

    public Epic(long identifier, String caption, String image, Coord2D centroid, Coord3D epicPosition,
                Coord3D moonPosition, Coord3D sunPosition, String date, boolean isNatural, boolean isFavorite){

        this.identifier = identifier;
        this.caption = caption;
        this.image = image;
        this.centroid = centroid;
        this.epicPosition = epicPosition;
        this.moonPosition = moonPosition;
        this.sunPosition = sunPosition;
        this.date = date;
        this.isNatural = isNatural;
        this.isFavorite = isFavorite;

    }

    public long getIdentifier() {
        return identifier;
    }

    public String getCaption() {
        return caption;
    }

    public String getImage() {
        return image;
    }

    public Coord2D getCentroid() {
        return centroid;
    }

    public Coord3D getEpicPosition() {
        return epicPosition;
    }

    public Coord3D getMoonPosition() {
        return moonPosition;
    }

    public Coord3D getSunPosition() {
        return sunPosition;
    }

    public String getDate() {
        return date;
    }

    public AttitudeQuaternions getAttitudeQuaternions() {
        return attitudeQuaternions;
    }

    public boolean isNatural() {
        return isNatural;
    }


    public void setNatural(boolean natural) {
        isNatural = natural;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    /**
     * Coord2D represents a Earth Coordinate as latitude and longitude
     */
    public static class Coord2D{
        double lat;
        Double lon;

        public Coord2D(double lat, double lon){
            this.lat = lat;
            this.lon = lon;
        }

        public double getLat() {
            return lat;
        }

        public Double getLon() {
            return lon;
        }
    }

    /**
     * Coord3D represents a Coordinate in Space relative to Earth.
     * Earth is at x = 0, y = 0, z = 0
     */

    public static class Coord3D{
        Double x;
        Double y;
        Double z;

        public Coord3D(double x, double y, double z){
            this.x = x;
            this.y = y;
            this.z = z;
        }

        public Double getX() {
            return x;
        }

        public Double getY() {
            return y;
        }

        public Double getZ() {
            return z;
        }
    }

    /**
     * Orientation of the DSCVR expressed as quaternions
     * https://en.wikipedia.org/wiki/Quaternions_and_spatial_rotation
     */

    public static class AttitudeQuaternions{
        double q0;
        double q1;
        double q2;
        double q3;

        public AttitudeQuaternions(double q0, double q1, double q2, double q3){
            this.q0 = q0;
            this.q1 = q1;
            this.q2 = q2;
            this.q3 = q3;
        }


        public double getQ0() {
            return q0;
        }

        public double getQ1() {
            return q1;
        }

        public double getQ2() {
            return q2;
        }

        public double getQ3() {
            return q3;
        }
    }


}
