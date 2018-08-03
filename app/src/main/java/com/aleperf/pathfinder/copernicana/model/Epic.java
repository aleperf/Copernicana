package com.aleperf.pathfinder.copernicana.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Epic represents a picture by the DSCOVR's Earth Polychromatic Imaging Camera (EPIC) instrument
 * as provided by the EPIC API.
 * More info about the API at https://epic.gsfc.nasa.gov/about/api
 */
@Entity(tableName = "epic")
public class Epic implements Parcelable{

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
    String date;
    private int isFavorite;
    private static final Coord3D earthPosition = new Coord3D(0, 0, 0);

    public Epic(long identifier, String caption, String image, Coord2D centroid, Coord3D epicPosition,
                Coord3D moonPosition, Coord3D sunPosition, String date, int isFavorite) {

        this.identifier = identifier;
        this.caption = caption;
        this.image = image;
        this.centroid = centroid;
        this.epicPosition = epicPosition;
        this.moonPosition = moonPosition;
        this.sunPosition = sunPosition;
        this.date = date;
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



    public int isFavorite() {
        return isFavorite;
    }

    public void setFavorite(int favorite) {
        isFavorite = favorite;
    }

    private Epic(Parcel in){
        identifier = in.readLong();
        caption = in.readString();
        image = in.readString();
        this.centroid = in.readParcelable(Coord2D.class.getClassLoader());
        this.epicPosition = in.readParcelable(Coord3D.class.getClassLoader());
        this.moonPosition = in.readParcelable(Coord3D.class.getClassLoader());
        this.sunPosition = in.readParcelable(Coord3D.class.getClassLoader());
        this.date = in.readString();
        this.isFavorite = in.readInt();
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public Epic createFromParcel(Parcel in) {
            return new Epic(in);
        }

        public Epic[] newArray(int size) {
            return new Epic[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(identifier);
        dest.writeString(caption);
        dest.writeString(image);
        dest.writeParcelable(centroid, flags);
        dest.writeParcelable(epicPosition, flags);
        dest.writeParcelable(moonPosition, flags);
        dest.writeParcelable(sunPosition, flags);
        dest.writeString(date);
        dest.writeInt(isFavorite);

    }

    /**
     * Coord2D represents a Earth Coordinate as latitude and longitude
     */
    public static class Coord2D  implements Parcelable{
        double lat;
        double lon;

        public Coord2D(double lat, double lon) {
            this.lat = lat;
            this.lon = lon;
        }

        public double getLat() {
            return lat;
        }

        public double getLon() {
            return lon;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        private Coord2D(Parcel in){
            lat = in.readDouble();
            lon = in.readDouble();
        }

        @SuppressWarnings("unused")
        public static final Parcelable.Creator CREATOR
                = new Parcelable.Creator() {
            public Coord2D createFromParcel(Parcel in) {
                return new Coord2D(in);
            }

            public Coord2D[] newArray(int size) {
                return new Coord2D[size];
            }
        };

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(lat);
            dest.writeDouble(lon);
        }
    }

    /**
     * Coord3D represents a Coordinate in Space relative to Earth.
     * Earth is at x = 0, y = 0, z = 0
     */

    public static class Coord3D implements Parcelable{
        Double x;
        Double y;
        Double z;

        public Coord3D(double x, double y, double z) {
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

        private Coord3D(Parcel in){
            x = in.readDouble();
            y = in.readDouble();
            z = in.readDouble();
        }

        @SuppressWarnings("unused")
        public static final Parcelable.Creator CREATOR
                = new Parcelable.Creator() {
            public Coord3D createFromParcel(Parcel in) {
                return new Coord3D(in);
            }

            public Coord3D[] newArray(int size) {
                return new Coord3D[size];
            }
        };

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeDouble(x);
            dest.writeDouble(y);
            dest.writeDouble(z);
        }
    }



    public double getDistance(Coord3D point1, Coord3D point2) {

        double xDiff = point1.getX() - point2.getX();
        double yDiff = point1.getY() - point2.getY();
        double zDiff = point1.getZ() - point2.getZ();
         return Math.sqrt(xDiff * xDiff + yDiff * yDiff + zDiff * zDiff);
    }

    public long getDistanceEpicToEarth(){
        return Math.round(getDistance(epicPosition, earthPosition));
    }

    public long getDistanceEpicToSun(){
        return Math.round(getDistance(epicPosition, sunPosition));
    }

    public long getDistanceSunToEarth(){
        return Math.round(getDistance(sunPosition, earthPosition));
    }


}
