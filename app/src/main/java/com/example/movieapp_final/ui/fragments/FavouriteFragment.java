package com.example.movieapp_final.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp_final.R;
import com.example.movieapp_final.data.db.AppDatabase;
import com.example.movieapp_final.data.db.entities.Favourite;
import com.example.movieapp_final.ui.activities.DetailActivity;
import com.example.movieapp_final.ui.adapters.FavouriteAdapter;
import com.example.movieapp_final.ui.adapters.clicklistener.OnItemClick;

import java.util.List;

public class FavouriteFragment extends Fragment implements OnItemClick, SearchView.OnQueryTextListener {
    private RecyclerView recyclerView;
    private AppDatabase database;
    private TextView tvNotFound;
    private List<Favourite> favouriteList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_favourite, container, false);
        database = AppDatabase.getInstance(getActivity().getApplicationContext());

        tvNotFound = view.findViewById(R.id.tv_error);
        tvNotFound.setVisibility(View.INVISIBLE);

        recyclerView = view.findViewById(R.id.rv_favourite);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        loadData();
        return view;
    }

    private void loadData() {
        tvNotFound.setVisibility(View.INVISIBLE);
        database.favoriteDao().getAll().observe(this, new Observer<List<Favourite>>() {
            @Override
            public void onChanged(List<Favourite> favourites) {
                favouriteList = favourites;
                recyclerView.setAdapter(new FavouriteAdapter(favourites, FavouriteFragment.this));
                if(favourites.size()==0){
                    tvNotFound.setVisibility(View.VISIBLE);
                }
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar_tv_show_fragment,menu);
        MenuItem item = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(this);
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setFilteredFavorites(String s){
        tvNotFound.setVisibility(View.INVISIBLE);
        database.favoriteDao().getFiltered(s).observe(this, new Observer<List<Favourite>>() {
            @Override
            public void onChanged(List<Favourite> favorites) {
                favouriteList = favorites;
                recyclerView.setAdapter(new FavouriteAdapter(favorites, FavouriteFragment.this));
                if(favorites.size()==0){
                    tvNotFound.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onClick(int pos) {
        Intent detailActivity = new Intent(getActivity(), DetailActivity.class);
        detailActivity.putExtra("ID", favouriteList.get(pos).getId());
        startActivity(detailActivity);

    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }


    @Override
    public boolean onQueryTextChange(String s) {
        if(s.length()>0){
            s = "%"+s+"%";
            setFilteredFavorites(s);
        }else{
            loadData();
        }
        return true;
    }








}
