package com.aleperf.pathfinder.copernicana.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.widget.RemoteViews;

import com.aleperf.pathfinder.copernicana.R;
import com.aleperf.pathfinder.copernicana.apod.ApodActivity;
import com.aleperf.pathfinder.copernicana.apod.ApodDetailActivity;
import com.aleperf.pathfinder.copernicana.apod.ApodDetailAllActivity;
import com.aleperf.pathfinder.copernicana.intro.IntroActivity;
import com.aleperf.pathfinder.copernicana.intro.SignInActivity;
import com.aleperf.pathfinder.copernicana.model.Apod;
import com.aleperf.pathfinder.copernicana.utilities.Utils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.AppWidgetTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.flags.Flag;

/**
 * Implementation of App Widget functionality.
 */
public class CopernicanaAppWidgetProvider extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.copernicana_app_widget);
        Resources res = context.getResources();
        String apodTitleKey = res.getString(R.string.preference_latest_apod_title_key);
        String apodDefaultTitle = res.getString(R.string.preference_latest_apod_title_default);
        String apodImageUrlKey = res.getString(R.string.preference_latest_apod_image_key);
        String apodDefaultUrl = res.getString(R.string.preference_latest_apod_image_default);
        String apodMediaTypeKey = res.getString(R.string.preference_lastest_apod_media_type_key);
        String apodMediaTypeDefault = res.getString(R.string.preference_latest_apod_format_default);
        SharedPreferences sharedPref = context.getSharedPreferences(res.getString(R.string.preference_file_key),
                Context.MODE_PRIVATE);
        String apodTitle = sharedPref.getString(apodTitleKey, apodDefaultTitle);
        views.setTextViewText(R.id.apod_title_widget, apodTitle);
        String apodImageUrl = sharedPref.getString(apodImageUrlKey, apodDefaultUrl);
        String apodMediaType = sharedPref.getString(apodMediaTypeKey, apodMediaTypeDefault);
        if (!apodImageUrl.equals(apodDefaultUrl)) {
            if (apodMediaType.equals(Apod.MEDIA_TYPE_IMAGE)) {
                loadImageWithGlide(context, views, R.id.apod_image_widget, apodImageUrl, appWidgetId);
            } else if (apodMediaType.equals(Apod.MEDIA_TYPE_VIDEO)) {
                if (apodImageUrl.contains("youtube")) {
                    String videoUrlThumbnail = Utils.buildVideoThumbnailFromUrl(apodImageUrl);
                    loadImageWithGlide(context, views, R.id.apod_image_widget, videoUrlThumbnail, appWidgetId);
                } else {
                    views.setImageViewResource(R.id.apod_image_widget, R.drawable.nasa_43566_unsplash);
                }
            }
        } else {
            views.setImageViewResource(R.id.apod_image_widget, R.drawable.nasa_43566_unsplash);
        }
        //if there is data available set an onClick Intent (it opens up the apod section)
        if (!apodTitle.equals(apodDefaultTitle)) {
            Intent intent = new Intent(context, ApodActivity.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(context,
                    0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
            views.setOnClickPendingIntent(R.id.copernicana_widget_ll, pendingIntent);
        }

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    private static void loadImageWithGlide(Context context, RemoteViews views, int layout, String imageUrl, int appWidgetId) {
        AppWidgetTarget awt = new AppWidgetTarget(context, layout, views, appWidgetId) {
            @Override
            public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                super.onResourceReady(resource, transition);
            }
        };

        RequestOptions options = new RequestOptions().
                override(300, 300)
                .placeholder(R.drawable.apod_placeholder).
                        error(R.drawable.apod_error);


        Glide.with(context.getApplicationContext())
                .asBitmap()
                .load(imageUrl)
                .apply(options)
                .into(awt);
    }
}

