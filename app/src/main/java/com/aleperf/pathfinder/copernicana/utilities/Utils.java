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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    public static List<Astronaut> getAstronautsFromJson(JsonObject jsonObject){
        JsonArray jsonArray = jsonObject.getAsJsonArray("people");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Type astronautListType = new TypeToken<ArrayList<Astronaut>>(){}.getType();
        return gson.fromJson(jsonArray, astronautListType);
        }

    public static String getYoutubeIdFromUrl(String youtubeUrl){
        String youtubeId = "";
        String youtubeRegex = "^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$";
        Pattern pattern = Pattern.compile(youtubeRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher =pattern.matcher(youtubeUrl);
        if(matcher.find()){
            youtubeId = matcher.group(1);
        }
        return youtubeId;
    }


}
