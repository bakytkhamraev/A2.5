package com.geektech.taskapp.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.geektech.taskapp.Task;

@Database(entities = {Task.class}, version = 1, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao taskDao();
}
