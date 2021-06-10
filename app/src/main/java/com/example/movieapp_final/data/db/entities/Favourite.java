package com.example.movieapp_final.data.db.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "favorites")
public class Favourite implements Serializable {

    public Favourite(){

    }

    public Favourite(int id, String title, String imgUrl){
        this.id = id;
        this.title = title;
        this.imgUrl = imgUrl;
    }

    @PrimaryKey
    private int id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    private String title;

    private String imgUrl;

    public Favourite(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
