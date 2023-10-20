package com.victory.game.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class AppDataUtil {

    private static AppDataUtil instance;
    private SharedPreferences sharedPreferences;

    private AppDataUtil(Context context) {
        sharedPreferences = context.getSharedPreferences("APP_SHARED", Context.MODE_PRIVATE);
    }

    public static synchronized AppDataUtil getInstance(Context context) {
        if (instance == null) {
            instance = new AppDataUtil(context.getApplicationContext());
        }
        return instance;
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean("login",false);
    }

    public boolean getBooleanData(String key) {
        return sharedPreferences.getBoolean(key, false);
    }

    public boolean setBooleanData(boolean value, String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        return editor.commit();
    }

    public boolean setStringData(String value, String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        return editor.commit();
    }
    public boolean setIntData(int value, String key) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        return editor.commit();
    }
    public int getIntData(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public String getStringData(String key) {
        return sharedPreferences.getString(key, "");
    }

    public String decodeString(String encodedStr) {
        byte[] decodedBytes = android.util.Base64.decode(encodedStr, android.util.Base64.URL_SAFE);
        return new String(decodedBytes).trim();
    }

    public String encodeString(String normalString) {
        return android.util.Base64.encodeToString(normalString.getBytes(), android.util.Base64.URL_SAFE);
    }
}
