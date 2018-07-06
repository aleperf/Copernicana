package com.aleperf.pathfinder.copernicana.utilities;

import com.aleperf.pathfinder.copernicana.model.Astronaut;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class JsonConverter {

    public static List<Astronaut> getAstronautsFromJson(JsonObject jsonObject){
        JsonArray jsonArray = jsonObject.getAsJsonArray("people");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Type astronautListType = new TypeToken<ArrayList<Astronaut>>(){}.getType();
        List<Astronaut> astronauts = gson.fromJson(jsonArray, astronautListType);
        return astronauts;
    }
}
