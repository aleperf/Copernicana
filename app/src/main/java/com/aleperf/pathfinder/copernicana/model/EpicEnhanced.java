package com.aleperf.pathfinder.copernicana.model;


import android.arch.persistence.room.Entity;
import android.os.Parcelable;

@Entity(tableName = "epic_enhanced")
public class EpicEnhanced extends Epic {

    public EpicEnhanced(long identifier, String caption, String image, Coord2D centroid, Coord3D epicPosition,
                Coord3D moonPosition, Coord3D sunPosition, String date, int isFavorite) {

        super(identifier, caption, image, centroid, epicPosition, moonPosition, sunPosition, date, isFavorite);

    }

}
