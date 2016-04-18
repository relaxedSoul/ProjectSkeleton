package com.relaxedsoul.projectskeleton;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.relaxedsoul.projectskeleton.util.PrefUtils;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * Base application
 */
public class MyApp extends Application {

    private static MyApp sInstance;

    public static MyApp getInstance() {
        return sInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (sInstance == null) sInstance = this;
        JodaTimeAndroid.init(this);
        PrefUtils.init(this);

    }

    @SuppressWarnings("unused")
    public boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}
