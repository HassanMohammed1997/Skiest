package com.project.semicolon.skiest.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.lang.ref.WeakReference;

public class SharedPrefUtil {

    public static void save(Context context, String key, Object value) {
        WeakReference<Context> contextWeakReference = new WeakReference<>(context);
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(contextWeakReference.get());

        SharedPreferences.Editor editor = pref.edit();
        if (value instanceof Integer) {
            editor.putInt(key, (Integer) value);
        } else if (value instanceof Boolean) {
            editor.putBoolean(key, (Boolean) value);
        } else if (value instanceof String) {
            editor.putString(key, value.toString());
        } else if (value instanceof Float) {
            editor.putFloat(key, (Float) value);
        } else if (value instanceof Long) {
            editor.putLong(key, (Long) value);
        }

        editor.apply();


    }

    public static Object getData(Context context, String key, Object defaultValue) {
        WeakReference<Context> contextWeakReference = new WeakReference<>(context);
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(contextWeakReference.get());

        if (defaultValue instanceof String) {
            return pref.getString(key, defaultValue.toString());
        } else if (defaultValue instanceof Integer) {
            return pref.getInt(key, (Integer) defaultValue);
        } else if (defaultValue instanceof Boolean) {
            return pref.getBoolean(key, (Boolean) defaultValue);
        } else if (defaultValue instanceof Long) {
            return pref.getLong(key, (Long) defaultValue);
        }


        return defaultValue;

    }

    public static void remove(Context context, String key) {
        WeakReference<Context> contextWeakReference = new WeakReference<>(context);
        SharedPreferences pref = PreferenceManager
                .getDefaultSharedPreferences(contextWeakReference.get());

        SharedPreferences.Editor editor = pref.edit();
        editor.remove(key).apply();

    }

    public static boolean hasKey(Context context, String key) {
        WeakReference<Context> contextWeakReference = new WeakReference<>(context);
        SharedPreferences pref = PreferenceManager.
                getDefaultSharedPreferences(contextWeakReference.get());
        return pref.contains(key);

    }
}
