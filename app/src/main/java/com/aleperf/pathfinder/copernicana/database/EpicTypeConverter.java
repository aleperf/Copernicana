package com.aleperf.pathfinder.copernicana.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import java.lang.reflect.Type;
import com.aleperf.pathfinder.copernicana.model.Epic.Coord2D;
import com.aleperf.pathfinder.copernicana.model.Epic.Coord3D;

import com.google.gson.reflect.TypeToken;

public class EpicTypeConverter {

    private static Gson gson = new Gson();

    @TypeConverter
    public static Coord2D convertJsonStringToCoord2D(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }

        Type coord2DType = new TypeToken<Coord2D>() {
        }.getType();

        return gson.fromJson(jsonString, coord2DType);
    }

    @TypeConverter
    public static String convertCoord2DToJson(Coord2D coord2D) {

        return gson.toJson(coord2D);
    }

    @TypeConverter
    public static Coord3D convertJsonStringToCoord3D(String jsonString) {
        if (jsonString == null || jsonString.isEmpty()) {
            return null;
        }

        Type coord3DType = new TypeToken<Coord3D>() {
        }.getType();

        return gson.fromJson(jsonString, coord3DType);
    }

    @TypeConverter
    public static String convertCoord3DToJson(Coord3D coord3D) {

        return gson.toJson(coord3D);
    }

}
