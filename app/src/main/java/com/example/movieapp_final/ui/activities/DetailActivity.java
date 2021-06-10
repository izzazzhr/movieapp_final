package com.example.movieapp_final.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.movieapp_final.ImageSize;
import com.example.movieapp_final.R;
import com.example.movieapp_final.data.api.Service;
import com.example.movieapp_final.data.api.repository.callback.OnCastCallback;
import com.example.movieapp_final.data.db.AppDatabase;
import com.example.movieapp_final.data.db.entities.Favourite;
import com.example.movieapp_final.data.models.Cast;
import com.example.movieapp_final.data.models.CreditModel;
import com.example.movieapp_final.data.models.Genre;
import com.example.movieapp_final.data.models.Trailer;
import com.example.movieapp_final.data.api.repository.TvShowRepository;
import com.example.movieapp_final.data.api.repository.callback.OnTvDetailCallback;
import com.example.movieapp_final.data.models.TvShow;
import com.example.movieapp_final.ui.adapters.CastAdapter;
import com.example.movieapp_final.ui.adapters.GenreAdapter;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private int id;
    private TvShowRepository repository;
    private ImageView ivBackdrop;
    private ImageView ivPoster;
    private TextView tvName;
    private RatingBar rbRate;
    private TextView tvFirstAirDate;
    private TextView tvLastAirDate;
    private TextView tvSeason;
    private TextView tvEpisodes;
    private TextView tvOverview;
    private TextView tvError;
    private String favTitle;
    private String favPosterPath = "";
    private AppDatabase database;
    private CastAdapter castAdapter;
    private TvShow tvShow;
    private List<Cast> castList;
    private ArrayList<String> genres;
    private RecyclerView rvCast, rvGenre;
    private LinearLayoutManager manager;
    private String selectedFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setActionBar("");

        castList = new ArrayList<>();
        genres = new ArrayList<>();
        ivBackdrop = findViewById(R.id.iv_backdrop);
        ivPoster = findViewById(R.id.iv_poster_detail);
        rbRate = findViewById(R.id.rb_rating_details);
        tvName = findViewById(R.id.tv_title_details);
        tvFirstAirDate = findViewById(R.id.tv_first_air_date);
        tvLastAirDate = findViewById(R.id.tv_last_air_date);
        tvOverview = findViewById(R.id.tv_overview);
        tvSeason = findViewById(R.id.tv_season);
        tvEpisodes = findViewById(R.id.tv_episode);
        tvError = findViewById(R.id.tv_error);
        rvCast = findViewById(R.id.rv_cast);
        repository = TvShowRepository.getInstance();


        rvGenre = findViewById(R.id.rv_genre);


        if (getIntent() != null) {
            id = getIntent().getIntExtra("ID", 0);
        }


        database = AppDatabase.getInstance(getApplicationContext());
        repository = TvShowRepository.getInstance();
        getRepositoryData(id);


    }


    @Override
    public boolean onCreateOptionsMenu(@NonNull Menu menu){
        getMenuInflater().inflate(R.menu.action_bar_detail_activity, menu);
        int tvShowId = id;
        boolean exists = database.favoriteDao().isExists(tvShowId);

        if(!exists){
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24));

        }else{
            menu.getItem(0).setIcon(ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_24));
        }
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent mainActivity = new Intent(DetailActivity.this, MainActivity.class);
                mainActivity.putExtra("SELECTED_FRAGMENT", getIntent().getStringExtra("SELECTED_FRAGMENT"));
                startActivity(mainActivity);
                return true;
            case R.id.item_favorite:
                int tvShowId = id;
                boolean exists = database.favoriteDao().isExists(tvShowId);

                if(exists){
                    Favourite favourite = database.favoriteDao().findById(tvShowId);
                    database.favoriteDao().delete(favourite).subscribe(()->{
                        item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_border_24));
                        Toast.makeText(this, "Removed from Favourite", Toast.LENGTH_SHORT).show();
                    }, throwable -> {
                        Toast.makeText(this,"Operation Failed", Toast.LENGTH_SHORT).show();
                    });
                }else{
                    Favourite favourite = new Favourite(tvShowId, favTitle, favPosterPath);
                    database.favoriteDao().addFavorite(favourite).subscribe(()->{
                        item.setIcon(ContextCompat.getDrawable(this, R.drawable.ic_baseline_favorite_24));
                        Toast.makeText(this, "Added to Favorite", Toast.LENGTH_SHORT).show();

                    }, throwable -> {
                        Toast.makeText(this, "Failed To Add", Toast.LENGTH_SHORT).show();
                    });

                }

                return true;

            case R.id.item_language_setting:
                startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void getRepositoryData(int id) {
        repository.getTvDetail(id, new OnTvDetailCallback() {
            @Override
            public void onSuccess(TvShow tvShow, String message) {
                onBindView(tvShow);
            }

            @Override
            public void onFailure(String message) {

            }
        });
    }

    private void onBindView(TvShow tvShow) {
        this.tvShow = tvShow;

        favTitle = tvShow.getName();
        favPosterPath = tvShow.getPosterPath(ImageSize.W154);


        setActionBar(tvShow.getName());
        Glide.with(DetailActivity.this)
                .load(tvShow.getBackdropPath(ImageSize.W500))
                .into(ivBackdrop);
        Glide.with(DetailActivity.this)
                .load(tvShow.getPosterPath(ImageSize.W154))
                .into(ivPoster);
        tvName.setText(tvShow.getName());
        rbRate.setRating(tvShow.getVoteAverage() / 2);
        tvLastAirDate.setText(tvShow.getLastAirDate());
        tvFirstAirDate.setText(tvShow.getFirstAirDate());
        tvEpisodes.setText(String.valueOf(tvShow.getNumberOfEpisode()));
        tvSeason.setText(String.valueOf(tvShow.getNumberOfSeason()));
        tvOverview.setText(tvShow.getOverview());
        setGenres(tvShow.getGenres());
        rvGenre.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        rvGenre.setAdapter(new GenreAdapter(genres, this));

        loadCastData(id);

    }

    private void setGenres(List<Genre> genreList) {
        for (int i = 0; i < genreList.size(); i++) {
            genres.add(genreList.get(i).getName());

        }
    }

    private void loadCastData(int id) {
        repository.getTvShowCast(id, new OnCastCallback() {
            @Override
            public void onSuccess(CreditModel creditModel, String message) {
                castList = creditModel.getCast();
                rvCast.setLayoutManager(new LinearLayoutManager(DetailActivity.this
                , RecyclerView.HORIZONTAL, false));
                rvCast.setAdapter(new CastAdapter(castList, DetailActivity.this));

            }

            @Override
            public void onFailure(String message) {

            }
        });
    }


    private void setActionBar(String s) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#082C59")));
        if(actionBar != null){
            actionBar.setTitle(s);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}