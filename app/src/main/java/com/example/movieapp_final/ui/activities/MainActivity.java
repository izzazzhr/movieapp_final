package com.example.movieapp_final.ui.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.movieapp_final.R;
import com.example.movieapp_final.ui.fragments.FavouriteFragment;
import com.example.movieapp_final.ui.fragments.TvShowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView bottomNav = findViewById(R.id.bnv_main);
        bottomNav.setOnNavigationItemSelectedListener(this);
        setSelectedItem(bottomNav);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;
        String sortBy = null;

        switch (item.getItemId()){
            case R.id.item_airing_today:
                setActionBar(getString(R.string.airing_today), R.drawable.ic_baseline_update_24);
                sortBy = "airing_today";
                fragment = new TvShowFragment();
                break;
            case R.id.item_top_rated:
                setActionBar(getString(R.string.top_rated), R.drawable.ic_baseline_bar_chart_24);
                sortBy = "top_rated";
                fragment = new TvShowFragment();
                break;
            case R.id.item_popular:
                setActionBar(getString(R.string.popular), R.drawable.ic_baseline_explore_24);
                sortBy = "popular";
                fragment = new TvShowFragment();
                break;
            case R.id.item_favourite:
                setActionBar(getString(R.string.favourite), R.drawable.ic_baseline_star_24);
                fragment = new FavouriteFragment();
                break;

        }
        if(fragment != null){
            startFragment(fragment, sortBy);
            return true;
        }
        return false;
    }

    private void setActionBar(String title, int logo) {
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#082C59")));
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle("\t" + title);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayUseLogoEnabled(true);
            getSupportActionBar().setLogo(logo);
        }
    }

    private void setSelectedItem(BottomNavigationView bottomNav) {
        if(getIntent().getStringExtra("SELECTED_FRAGMENT") != null){
            switch (getIntent().getStringExtra("SELECTED_FRAGMENT")){
                case "airing_today":
                    bottomNav.setSelectedItemId(R.id.item_airing_today);
                    break;
                case "top_rated":
                    bottomNav.setSelectedItemId(R.id.item_top_rated);
                    break;
                case "popular":
                    bottomNav.setSelectedItemId(R.id.item_popular);
                    break;
                case "favourite":
                    bottomNav.setSelectedItemId(R.id.item_favourite);
                    break;
            }
        }else{
            bottomNav.setSelectedItemId(R.id.item_airing_today);
        }
    }

    private void startFragment(Fragment fragment, String bundle) {
        if(bundle != null){
            Bundle sortBy = new Bundle();
            sortBy.putString("SORT_BY", bundle); 
            fragment.setArguments(sortBy);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fl_main, fragment).commit();

    }


}