package com.example.movieapp_final.data.db.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.movieapp_final.data.db.entities.Favourite;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;

@Dao
public interface FavoriteDao {
    @Query("SELECT * FROM favorites")
    LiveData<List<Favourite>> getAll();

    @Query("SELECT * FROM favorites WHERE title LIKE :title")
    LiveData<List<Favourite>> getFiltered(String title);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable addFavorite(Favourite favorite);

    @Query("SELECT EXISTS (SELECT * FROM favorites WHERE id = :id )")
    boolean isExists(int id);

    @Query("SELECT * FROM favorites WHERE id=:id LIMIT 1 ")
    Favourite findById(int id);

    @Delete
    Completable delete(Favourite favorite);
}
