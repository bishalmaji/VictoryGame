package com.victory.game;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class AppDataUtil {
    private Activity activity;
    SharedPreferences sharedPreferences;

    public AppDataUtil(Activity activity) {
        this.activity = activity;
        sharedPreferences= activity.getSharedPreferences("APP_SHARED", Context.MODE_PRIVATE);
    }
    public boolean getBooleanData(String key){
        return sharedPreferences.getBoolean(key,false);
    }
    public boolean putBooleanData(boolean value, String key){
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean(key,value);
        return editor.commit();
    }
}
