package com.example.notedatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;


@Database(version = 1,exportSchema = false,entities = {Task.class})
public abstract class AppDataBase extends RoomDatabase {
    private static AppDataBase appDataBase;

    public static AppDataBase getAppDataBase(Context context){
            if (appDataBase==null){
                appDataBase= Room.databaseBuilder(context.getApplicationContext() , AppDataBase.class , "Note DataBase")
                        .allowMainThreadQueries().build();
            }
        return appDataBase;
    }

    public abstract TaskDao getTaskDao();
}
