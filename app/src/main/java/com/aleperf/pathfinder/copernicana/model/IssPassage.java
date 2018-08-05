package com.aleperf.pathfinder.copernicana.model;

public class IssPassage {

    private int duration;
    private long risetime;
    public static int ISS_DURATION_NO_DATA = -1000;
    public static int ISS_DURATION_FAILED_CONNECTION = -5000;

    public IssPassage(int duration, long risetime){
        this.duration = duration;
        this.risetime = risetime;
    }

    public int getDuration() {
        return duration;
    }

    public long getRisetime() {
        return risetime;
    }
}
