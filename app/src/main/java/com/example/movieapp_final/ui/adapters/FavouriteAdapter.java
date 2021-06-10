package com.example.movieapp_final.ui.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.movieapp_final.Const;
import com.example.movieapp_final.R;
import com.example.movieapp_final.data.db.entities.Favourite;
import com.example.movieapp_final.ui.adapters.clicklistener.OnItemClick;

import java.util.List;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.ViewHolder>{
    private List<Favourite> favourites;
    private OnItemClick onItemClick;

    public  FavouriteAdapter(List<Favourite> favorites, OnItemClick onItemClick){
        this.favourites = favorites;
        this.onItemClick = onItemClick;
    }

    @NonNull
    @Override
    public FavouriteAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view, onItemClick);
    }

    @Override
    public void onBindViewHolder(@NonNull  FavouriteAdapter.ViewHolder holder, int position) {
        Glide.with(holder.itemView.getContext())
                .load(Const.IMG_URL_200 + favourites.get(position).getImgUrl())
                .apply(RequestOptions.bitmapTransform(new RoundedCorners(25)))
                .into(holder.ivPoster);
        holder.tvName.setText(favourites.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return favourites.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        OnItemClick onItemClick;
        ImageView ivPoster;
        TextView tvName;
        TextView tvYear;

        public ViewHolder(@NonNull View itemView, OnItemClick onItemClick) {
            super(itemView);
            itemView.setOnClickListener(this);
            ivPoster = itemView.findViewById(R.id.iv_poster);
            tvName = itemView.findViewById(R.id.tv_name);
            tvYear = itemView.findViewById(R.id.tv_release_year);
            this.onItemClick = onItemClick;
        }


        @Override
        public void onClick(View v) {
           onItemClick.onClick(getAdapterPosition());

        }
    }
}
