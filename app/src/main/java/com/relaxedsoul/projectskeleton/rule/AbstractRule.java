package com.relaxedsoul.projectskeleton.rule;

import android.content.Context;

import com.relaxedsoul.projectskeleton.MyApp;

public abstract class AbstractRule {

    protected static Context getContext() {
        return MyApp.getInstance().getApplicationContext();
    }

    protected static String getString(int res) {
        return getContext().getString(res);
    }
}
