package com.example.movieapp_final;

import java.util.Locale;

public class Const {

    public static final String API_KEY = "d4928ba2d1bc836d81bd56435146cc3a";
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static final String BASE_URL_SEARCH = "https://api.themoviedb.org/3/search/";

    public static final String IMG_URL = "https://image.tmdb.org/t/p/";

    public static final String IMG_URL_ORI = "https://image.tmdb.org/t/p/original/";
    public static final String IMG_URL_200 = "https://image.tmdb.org/t/p/w200/";
    public static final String IMG_URL_300 = "https://image.tmdb.org/t/p/w300/";

    public static final String BASE_URL_GENRE = "https://api.themoviedb.org/3/genre/";



    public static String getLang() {
        switch (Locale.getDefault().toString()) {
            case "in_ID":
                return "id-ID";
            default:
                return "en-US";
        }
    }

}
