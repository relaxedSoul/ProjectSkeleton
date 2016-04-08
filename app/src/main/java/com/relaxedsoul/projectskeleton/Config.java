package com.relaxedsoul.projectskeleton;

import android.os.Build;

/**
 * Created by yurchenko on 23.03.2016.
 */
public abstract class Config {

    public static final boolean DEBUG = true;
    public static final boolean NET_DEBUG = DEBUG;
    public static final boolean LOGS_ENABLED = DEBUG;

    public static Class constants = BuildConfig.class;

    public static boolean isPreAndroidM() {
        return Build.VERSION.SDK_INT < Build.VERSION_CODES.M;
    }
}
