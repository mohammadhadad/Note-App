package com.example.notedatabase;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    long add(Task task);



    @Delete
    int delete(Task task);


    @Update
    int update(Task task);


     @Query("SELECT * FROM `task table`")
    List<Task> gettask();


    @Query("SELECT * FROM `task table` WHERE Task LIKE '%'|| :Query|| '%'")
    List<Task> search(String Query);


    @Query("DELETE FROM `task table`")
    void deleteAll();
}
