package com.aleperf.pathfinder.copernicana.model;
import com.aleperf.pathfinder.copernicana.model.Epic.Coord2D;
import com.aleperf.pathfinder.copernicana.model.Epic.Coord3D;
import com.aleperf.pathfinder.copernicana.model.Epic.AttitudeQuaternions;


public interface EpicElement {

    long getIdentifier();

    String getCaption();

    String getImage();

    Coord2D getCentroid();

    Coord3D getEpicPosition();

    Coord3D getMoonPosition();

    Coord3D getSunPosition();

    String getDate();

    AttitudeQuaternions getAttitudeQuaternions();

    int isFavorite();

    void setFavorite(int favorite);

    public double getDistance(Coord3D point1, Coord3D point2);

    public long getDistanceEpicToEarth();

    public long getDistanceEpicToSun();

    public long getDistanceSunToEarth();
}
