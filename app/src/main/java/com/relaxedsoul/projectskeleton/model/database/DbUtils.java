package com.relaxedsoul.projectskeleton.model.database;

public abstract class DbUtils {

    private static final String TAG = "DbUtils";

    public static DatabaseHelper getHelper() {
        return DatabaseHelper.getInstance();
    }
}
