package com.geektech.taskapp;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.geektech.taskapp.Room.AppDatabase;

public class App extends Application {

    private static  App instance;

    private static AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance=this;

        database= Room.databaseBuilder(this, AppDatabase.class, "database")
                .allowMainThreadQueries()
                .build();


    }

    public  static App getInstance() {
        return instance;
    }

    public static AppDatabase getDatabase() {
        return database;
    }
}
