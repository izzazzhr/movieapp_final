package com.example.movieapp_final.data.models;

import com.example.movieapp_final.Const;
import com.example.movieapp_final.ImageSize;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TvShow {
    @SerializedName("backdrop_path")
    private String backdropPath;
    @SerializedName("first_air_date")
    private String firstAirDate;
    @SerializedName("id")
    private int id;
    @SerializedName("last_air_date")
    private String lastAirDate;
    @SerializedName("name")
    private String name;
    @SerializedName("number_of_episodes")
    private int numberOfEpisode;
    @SerializedName("number_of_seasons")
    private int numberOfSeason;
    @SerializedName("overview")
    private String overview;
    @SerializedName("popularity")
    private float popularity;
    @SerializedName("poster_path")
    private String posterPath;
    @SerializedName("vote_average")
    private float voteAverage;
    @SerializedName("vote_count")
    private int voteCount;

    @SerializedName("genres")
    @Expose
    private List<Genre> genres;

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public String getBackdropPath(ImageSize size) {
        return Const.IMG_URL + size.getValue() + backdropPath;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public int getId() {
        return id;
    }

    public String getLastAirDate() {
        return lastAirDate;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfEpisode() {
        return numberOfEpisode;
    }

    public int getNumberOfSeason() {
        return numberOfSeason;
    }

    public String getOverview() {
        return overview;
    }

    public float getPopularity() {
        return popularity;
    }

    public String getPosterPath(ImageSize size) {
        return Const.IMG_URL + size.getValue() + posterPath;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public int getVoteCount() {
        return voteCount;
    }


    public String getYear() {
        if (firstAirDate != null){
            String[] relaseYear = firstAirDate.split("-");
            return relaseYear[0];
        }else{
            return "2021";
        }


    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLastAirDate(String lastAirDate) {
        this.lastAirDate = lastAirDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNumberOfEpisode(int numberOfEpisode) {
        this.numberOfEpisode = numberOfEpisode;
    }

    public void setNumberOfSeason(int numberOfSeason) {
        this.numberOfSeason = numberOfSeason;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setPopularity(float popularity) {
        this.popularity = popularity;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setVoteCount(int voteCount) {
        this.voteCount = voteCount;
    }

}
