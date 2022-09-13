package com.galaxy.restaurantpos;

import android.app.Application;

import me.myatminsoe.mdetect.MDetect;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        MDetect.INSTANCE.init(this);
    }
}
