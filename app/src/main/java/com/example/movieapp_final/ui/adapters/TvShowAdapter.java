package com.example.movieapp_final.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp_final.ImageSize;
import com.example.movieapp_final.R;
import com.example.movieapp_final.data.models.TvShow;
import com.example.movieapp_final.ui.adapters.clicklistener.OnItemClick;
import com.example.movieapp_final.ui.adapters.clicklistener.OnItemClickListener;

import java.util.List;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewHolder> {
    private List<TvShow> tvShowList;
    private OnItemClickListener clickListener;
    
    public TvShowAdapter(List<TvShow> tvShowList){
        this.tvShowList = tvShowList;
    }

    public TvShowAdapter(List<TvShow> tvShowList, OnItemClickListener clickListener){
        this.tvShowList = tvShowList;
        this.clickListener = clickListener;
    }

    public void setClickListener(OnItemClickListener clickListener){
        this.clickListener = clickListener;
    }
    
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.onBindItemView(tvShowList.get(position));

    }

    public void appendList(List<TvShow> listToAppend){
        tvShowList.addAll(listToAppend);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return tvShowList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TvShow tvShow;
        ImageView ivPoster;
        TextView tvName;
        TextView tvYear;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_poster);
            tvName = itemView.findViewById(R.id.tv_name);
            tvYear = itemView.findViewById(R.id.tv_release_year);
            itemView.setOnClickListener(this);
        }

        public void onBindItemView(TvShow tvShow) {
            this.tvShow = tvShow;
            Glide.with(itemView.getContext()).load(tvShow.getPosterPath(ImageSize.W154)).into(ivPoster);
            tvName.setText(tvShow.getName());
            tvYear.setText(tvShow.getYear());
        }

        @Override
        public void onClick(View v) {
            clickListener.onClick(tvShow);

        }
    }
}
