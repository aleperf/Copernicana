package com.aleperf.pathfinder.copernicana.model;


import android.arch.persistence.room.Entity;
import android.os.Parcel;
import android.os.Parcelable;

@Entity(tableName = "epic_enhanced")
public class EpicEnhanced extends Epic implements Parcelable {

    public EpicEnhanced(long identifier, String caption, String image, Coord2D centroid, Coord3D epicPosition,
                Coord3D moonPosition, Coord3D sunPosition, String date, int isFavorite) {

        super(identifier, caption, image, centroid, epicPosition, moonPosition, sunPosition, date, isFavorite);

    }

    private EpicEnhanced(Parcel in) {
        super(in);
        }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public EpicEnhanced createFromParcel(Parcel in) {
            return new EpicEnhanced(in);
        }

        public EpicEnhanced[] newArray(int size) {
            return new EpicEnhanced[size];
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

}
