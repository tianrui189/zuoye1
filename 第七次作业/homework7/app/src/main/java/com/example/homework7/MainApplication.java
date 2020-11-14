package com.example.homework7;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Icon;
import android.util.Log;

import java.util.HashMap;

public class MainApplication extends Application {
    static MainApplication context ;
    private HashMap<String,String> minfomap  = new HashMap<>();
    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
    }
    public static  MainApplication getInstance () {
        return context;
    }
    @Override
    public void onTerminate() {
        Log.d("Help", "onTerminate");
        super.onTerminate();
    }

    public static HashMap<Long, Bitmap> mIconMap = new HashMap<>();
}
