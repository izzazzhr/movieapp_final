package com.example.movieapp_final.data.models;

import java.util.List;

public class CreditModel {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Cast> getCast() {
        return cast;
    }

    public void setCast(List<Cast> cast) {
        this.cast = cast;
    }

    private int id;
    private List<Cast> cast;
}
