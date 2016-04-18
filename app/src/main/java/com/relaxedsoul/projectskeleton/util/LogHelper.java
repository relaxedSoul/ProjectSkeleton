package com.relaxedsoul.projectskeleton.util;

import android.util.Log;

import com.relaxedsoul.projectskeleton.Config;

/**
 * Created by RelaxedSoul on 23.03.2016.
 */
public abstract class LogHelper {
    public static void i(String tag, String message) {
        if (Config.LOGS_ENABLED) {
            Log.i(tag, message);
        }
    }

    public static void d(String tag, String message) {
        if (Config.LOGS_ENABLED) {
            Log.d(tag, message);
        }
    }

    public static void w(String tag, String message) {
        if (Config.LOGS_ENABLED) {
            Log.w(tag, message);
        }
    }

    public static void w(String tag, String message, Throwable e) {
        if (Config.LOGS_ENABLED) {
            Log.w(tag, message, e);
        }
    }

    public static void e(String tag, String message) {
        if (Config.LOGS_ENABLED) {
            Log.e(tag, message);
        }
    }

    public static void method(String TAG, String methodName) {
        if (Config.DEBUG) Log.i(TAG, "on " + methodName);
    }

    public static String getTag(Class<?> clazz) {
        return "MF::" + clazz.getSimpleName();
    }

    public static void printStackTrace(Throwable e) {
        if (Config.LOGS_ENABLED) {
            e.printStackTrace();
        }
    }
}
