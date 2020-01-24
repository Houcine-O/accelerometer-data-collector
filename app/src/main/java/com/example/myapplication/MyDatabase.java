package com.example.myapplication;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Recolt.class}, version = 8,exportSchema = false)
abstract class MyDatabase extends RoomDatabase {

    public static final String DB_Racolt = "app_db";
    public static final String Racolt = "todo";
     abstract DaoAccess daoAccess();
}
