package com.example.easytodolist;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
@Dao
public interface TodoDao {
    @Insert
    void insert(Todo todo);
    @Update
    void update(Todo todo);
    @Delete
    void delete(Todo todo);
    @Query("DELETE FROM todo_table")
    void deleteAllTodo();
    @Query("SELECT * FROM todo_table ORDER BY date,time ASC")
    LiveData<List<Todo>> getAllTodo();

}
