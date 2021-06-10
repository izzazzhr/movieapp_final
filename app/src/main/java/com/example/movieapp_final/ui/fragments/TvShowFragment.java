package com.example.movieapp_final.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.movieapp_final.Const;
import com.example.movieapp_final.R;
import com.example.movieapp_final.data.api.repository.SearchApiClient;
import com.example.movieapp_final.data.api.repository.SearchApiInterface;
import com.example.movieapp_final.data.api.repository.TvShowRepository;
import com.example.movieapp_final.data.api.repository.callback.OnSearchCallback;
import com.example.movieapp_final.data.api.repository.callback.OnTvShowCallback;
import com.example.movieapp_final.data.models.TvShow;
import com.example.movieapp_final.data.models.TvShowResponse;
import com.example.movieapp_final.ui.activities.DetailActivity;
import com.example.movieapp_final.ui.adapters.TvShowAdapter;
import com.example.movieapp_final.ui.adapters.clicklistener.OnItemClickListener;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TvShowFragment extends Fragment
        implements OnItemClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        SearchView.OnQueryTextListener {
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private TvShowAdapter adapter;
    private TvShowRepository repository;
    private TextView tvError;
    private boolean isFetching;
    private int currentPage = 1;
    private List<TvShow> tvShows;
    private GridLayoutManager layoutManager;
    private String sortBy;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar_tv_show_fragment, menu);
        MenuItem item = menu.findItem(R.id.item_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(this);
//        setSearchView(searchView);
        super.onCreateOptionsMenu(menu, inflater);
    }

//    private void setSearchView(SearchView searchView) {
//        searchView.setIconifiedByDefault(true);
//        searchView.setQueryHint(getString(R.string.search));
//        searchView.setOnQueryTextListener(this);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_language_setting) {
            startActivity(new Intent(Settings.ACTION_LOCALE_SETTINGS));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tv_show, container, false);
        refreshLayout = view.findViewById(R.id.swl_tv_show);
        recyclerView = view.findViewById(R.id.rv_tv_show);
        tvError = view.findViewById(R.id.tv_error);
        repository = TvShowRepository.getInstance();
        getRepositoryData("", currentPage);
        onScrollListener();
        refreshLayout.setOnRefreshListener(this);
        return view;
    }


    private void onScrollListener() {
        final GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                int totalItem = layoutManager.getItemCount();
                int visibleItem = layoutManager.getChildCount();
                int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                if (firstVisibleItem + visibleItem >= totalItem / 2) {
                    if (!isFetching) {
                        isFetching = true;
                        currentPage ++;
                        getRepositoryData("", currentPage);

                        isFetching = false;
                    }
                }
            }
        });
    }



    private void getRepositoryData(String query, int page) {
        isFetching = true;
        if (query.equals("")) {
            repository.getTvShow(getBundle(), page, new OnTvShowCallback() {
                @Override
                public void onSuccess(int page, List<TvShow> tvShowList) {

                    if (adapter == null) {
                        adapter = new TvShowAdapter(tvShowList);
                        adapter.setClickListener(TvShowFragment.this);
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                    } else {
                        adapter.appendList(tvShowList);
                    }
                    currentPage = page;
                    isFetching = false;
                    refreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(String message) {
                    Log.d("TvShow","onFailure: " + message);
                    Toast.makeText(getActivity(), "Failed " + message, Toast.LENGTH_LONG).show();
                }
            });
        } else {
            repository.search(query, page, new OnSearchCallback() {
                @Override
                public void onSuccess(List<TvShow> tvShowList, String msg, int page) {

                    if (adapter == null) {
                        adapter = new TvShowAdapter(tvShowList);
                        adapter.setClickListener(TvShowFragment.this);
                        adapter.notifyDataSetChanged();
                        recyclerView.setAdapter(adapter);
                    } else {
                        adapter.appendList(tvShowList);
                    }
                    currentPage = page;
                    isFetching = false;
                    refreshLayout.setRefreshing(false);
                }

                @Override
                public void onFailure(String msg) {
                    Log.d("TvShow","onFailure: " + msg);
                    Toast.makeText(getActivity(), "Failed " + msg, Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void searchTvShow(String keyword, int page){
        isFetching = true;
        SearchApiInterface searchApiInterface = SearchApiClient.getRetrofit().create(SearchApiInterface.class);{
            Call<TvShowResponse> searchTvShowCall = searchApiInterface.getSearchResult(Const.API_KEY, keyword, page);
            searchTvShowCall.enqueue(new Callback<TvShowResponse>() {
                @Override
                public void onResponse(Call<TvShowResponse> call, Response<TvShowResponse> response) {
                    try{
                        if(response.isSuccessful() && response.body().getResults() != null){
                            if(adapter == null){
                                tvShows = response.body().getResults();
                                adapter = new TvShowAdapter(tvShows, TvShowFragment.this);
                                adapter.notifyDataSetChanged();
                                recyclerView.setAdapter(adapter);
                            }else{
                                adapter.appendList(response.body().getResults());
                            }
                            currentPage = page;
                            isFetching = false;
                            refreshLayout.setRefreshing(false);
                        }else{
                            Toast.makeText(getActivity(), response.errorBody().string(), Toast.LENGTH_LONG).show();

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(Call<TvShowResponse> call, Throwable t) {
                    Log.d("TV Show", "onFailure: " + t.getLocalizedMessage());
                    Toast.makeText(getActivity(), "Failed" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private String getBundle() {
        if (getArguments() != null) {
            return getArguments().getString("SORT_BY");
        }
        return "airing_today";
    }

    @Override
    public void onClick(TvShow tvShow) {
        Intent detailActivity = new Intent(getContext(), DetailActivity.class);
        detailActivity.putExtra("ID", tvShow.getId());
        detailActivity.putExtra("SELECTED_FRAGMENT", getBundle());
        startActivity(detailActivity);


    }



    @Override
    public void onRefresh() {
        adapter = null;
        getRepositoryData("", currentPage);
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (s.length() > 0) {
            adapter = null;
            searchTvShow(s, currentPage);
        } else {
            adapter = null;
            getRepositoryData("", currentPage);
        }
        return true;
    }


}