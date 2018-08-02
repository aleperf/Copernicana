package com.aleperf.pathfinder.copernicana.utilities;

import android.content.Context;

import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.model.Astronaut;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {



    public static List<Astronaut> getAstronautsFromJson(JsonObject jsonObject) {
        JsonArray jsonArray = jsonObject.getAsJsonArray("people");
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Type astronautListType = new TypeToken<ArrayList<Astronaut>>() {
        }.getType();
        return gson.fromJson(jsonArray, astronautListType);
    }

    public static String getYoutubeIdFromUrl(String youtubeUrl) {
        String youtubeId = "";
        String youtubeRegex = "^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$";
        Pattern pattern = Pattern.compile(youtubeRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(youtubeUrl);
        if (matcher.find()) {
            youtubeId = matcher.group(1);
        }
        return youtubeId;
    }

    public static String buildVideoThumbnailFromUrl(String youtubeUrl) {
        String youtubeId = getYoutubeIdFromUrl(youtubeUrl);
        String formatString = "https://img.youtube.com/vi/%s/0.jpg";
        return String.format(formatString, youtubeId);

    }

    public static String buildVideoThumbnailFromId(String youtubeId) {
        String formatString = "https://img.youtube.com/vi/%s/0.jpg";
        return String.format(formatString, youtubeId);
    }

    public static String getFormattedDate(String dateString, Context context) {
        String noDate = context.getString(R.string.apod_no_date);
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(dateString);
            SimpleDateFormat finalFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
            String result = finalFormat.format(date);
            return result;
        } catch (ParseException e) {
            return noDate;
        }
    }

    public static String getDateSearchString(int year, int month, int day){
        String dateformat = ("%d-%s-%s");
        int correctMonth = month + 1;
        String monthString = getNormalizedDateField(correctMonth);
        String dayString = getNormalizedDateField(day);
        return String.format(dateformat, year, monthString, dayString);
        }

    private static String getNormalizedDateField(int dateField){
        String normalizedField;
        String formatString ="0%d";
        if(dateField < 10){
            normalizedField = String.format(formatString, dateField);
        } else {
            normalizedField = String.valueOf(dateField);
        }
        return normalizedField;
        }


    public static String getLocalTimeFromUnixTimestamp(long timestamp){
        Calendar calendar = Calendar.getInstance();
        TimeZone timeZone = calendar.getTimeZone();
        SimpleDateFormat finalFormat = new SimpleDateFormat("MMM dd, yyyy  HH:mm:ss", Locale.ENGLISH);
        finalFormat.setTimeZone(timeZone);
        Date date = new Date(timestamp * 1000L);
        return finalFormat.format(date);

    }

    public static String buildEpicNaturalImageUrl(String date, String image){
        String formaString = "https://epic.gsfc.nasa.gov/archive/natural/%s/%s/%s/jpg/%s.jpg";
        String startDate = date.substring(0, 10);
        String[] dateElements = startDate.split("-");
        return String.format(formaString, dateElements[0], dateElements[1], dateElements[2], image);
    }

}
