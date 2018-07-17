package com.aleperf.pathfinder.copernicana.network;

import com.aleperf.pathfinder.copernicana.model.MarsPhoto;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MarsQuery {
    @SerializedName("latest_photos")
    List<MarsPhoto> photos;

    public List<MarsPhoto> getPhotos() {
        return photos;
    }
}
