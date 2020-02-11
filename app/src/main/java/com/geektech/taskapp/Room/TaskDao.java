package com.geektech.taskapp.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.geektech.taskapp.Task;

import java.util.List;

@Dao

public interface TaskDao {
@Query("SELECT*FROM task")
    List<Task>getAll();

    @Insert
    void insert(Task task);

    @Delete
    void delete(Task task);


    @Delete
    void deleteall(List<Task> list);
    @Update
    void update(Task task);



}
