package com.aleperf.pathfinder.copernicana.model;

import com.google.gson.annotations.SerializedName;

public class IssPosition {

    public final static String SUCCESS = "success";
    public final static String NO_CONNECTION = "no connection";

    long timestamp;
    @SerializedName("iss_position")
    Position issPosition;
    String message;

    public IssPosition(long timestamp, Position position, String message){
        this.timestamp = timestamp;
        this.issPosition = position;
        this.message = message;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public Position getIssPosition() {
        return issPosition;
    }

    public String getMessage() {
        return message;
    }

    public boolean isSuccess(){
        return message.equals(SUCCESS);
    }


    public static class Position {
        double latitude;
        double longitude;

        public double getLatitude() {
            return latitude;
        }

        public double getLongitude() {
            return longitude;
        }
    }
}
