package com.relaxedsoul.projectskeleton.model.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.relaxedsoul.projectskeleton.Config;
import com.relaxedsoul.projectskeleton.MyApp;
import com.relaxedsoul.projectskeleton.R;
import com.relaxedsoul.projectskeleton.model.Entity;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final Set<Class<?>> DATA_CLASSES = new HashSet<>(Arrays.asList(new Class<?>[]{
            Entity.class
    }));
    private static DatabaseHelper sInstance;
    private final boolean logging = Config.DEBUG;
    private RuntimeExceptionDao<Entity, Long> entityDao = null;

    // TODO redefine databaseName
    public DatabaseHelper(Context context) {
        super(context, context.getString(R.string.database_name), null,
                context.getResources().getInteger(R.integer.database_version));
    }

    public static synchronized DatabaseHelper getInstance() {
        if (sInstance == null) {
            sInstance = new DatabaseHelper(MyApp.getInstance());
        }
        return sInstance;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            if (logging) {
                Log.i(DatabaseHelper.class.getName(), "onCreate");
            }
            for (Class<?> dataClass : DATA_CLASSES) {
                TableUtils.createTable(connectionSource, dataClass);
            }
        } catch (SQLException e) {
            if (logging) {
                Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            }
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        if (oldVersion >= newVersion) return;
        if (logging) {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
        }
        try {
            for (Class<?> dataClass : DATA_CLASSES) {
                TableUtils.dropTable(connectionSource, dataClass, true);
            }
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            if (logging) {
                Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            }
            throw new RuntimeException(e);
        }
    }

    public <T> void clear(Class<T> clazz) {
        try {
            if (logging) {
                Log.i(DatabaseHelper.class.getName(), "onClearTable " + clazz.getName());
            }
            TableUtils.clearTable(connectionSource, clazz);
        } catch (SQLException e) {
            if (logging) {
                Log.e(DatabaseHelper.class.getName(), "Can't clear table " + clazz.getName(), e);
            }
            e.printStackTrace();
        }
    }

    public RuntimeExceptionDao<Entity, Long> getEntityDao() {
        if (entityDao == null) {
            entityDao = getRuntimeExceptionDao(Entity.class);
        }
        return entityDao;
    }

    @Override
    public <D extends RuntimeExceptionDao<T, ?>, T> D getRuntimeExceptionDao(Class<T> clazz) {
        D dao = super.getRuntimeExceptionDao(clazz);
        dao.setObjectCache(true);
        return dao;
    }

    public void close() {
        entityDao = null;
        super.close();
    }
}
