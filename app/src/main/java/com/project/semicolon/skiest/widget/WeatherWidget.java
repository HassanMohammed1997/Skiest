package com.project.semicolon.skiest.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.RemoteViews;

import com.project.semicolon.skiest.Constants;
import com.project.semicolon.skiest.R;
import com.project.semicolon.skiest.util.SharedPrefUtil;

/**
 * Implementation of App Widget functionality.
 */
public class WeatherWidget extends AppWidgetProvider {
    private static String temp, countryName;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.weather_widget);

        //get country name and current temp value from shared preference and display it in AppWidget
        countryName = String.valueOf(SharedPrefUtil.getData(context, Constants.KEY_ADDRESS, ""));
        temp = String.valueOf(SharedPrefUtil.getData(context, Constants.KEY_TEMP, ""));

        views.setTextViewText(R.id.county_name, countryName);
        views.setTextViewText(R.id.temp_text, temp);


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
        countryName = String.valueOf(SharedPrefUtil.getData(context, Constants.KEY_ADDRESS, ""));
        temp = String.valueOf(SharedPrefUtil.getData(context, Constants.KEY_TEMP, ""));
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

