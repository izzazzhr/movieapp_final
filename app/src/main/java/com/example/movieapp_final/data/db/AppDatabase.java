package com.example.movieapp_final.data.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.movieapp_final.data.db.dao.FavoriteDao;
import com.example.movieapp_final.data.db.entities.Favourite;

@Database(entities = {Favourite.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase{
    public abstract FavoriteDao favoriteDao();

    private static AppDatabase instance;

    public synchronized static AppDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "TV Show Source Database")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }

}