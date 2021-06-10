package com.example.movieapp_final.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class Genre {
    public static Map<Integer, String> genreList;

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
