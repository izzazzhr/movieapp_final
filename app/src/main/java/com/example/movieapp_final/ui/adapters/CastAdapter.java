package com.example.movieapp_final.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.movieapp_final.Const;
import com.example.movieapp_final.R;
import com.example.movieapp_final.data.models.Cast;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {

    private List<Cast> castList;
    private Context mContext;

    public CastAdapter(List<Cast> castList, Context context){
        this.castList = castList;
        this.mContext = context;
        System.out.println(castList.size());
    }

    @NonNull
    @NotNull
    @Override
    public CastAdapter.CastViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_cast, parent, false);
        return new CastAdapter.CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull  @NotNull CastViewHolder holder, int position) {
        holder.tvCastName.setText(castList.get(position).getName());
        holder.tvCastCharacter.setText(castList.get(position).getCharacter());
        Glide.with(holder.itemView.getContext()).load(Const.IMG_URL_200 + castList.get(position).getProfilePath()).into(holder.ivCastProfile);

    }

    @Override
    public int getItemCount() {
        return castList.size();
    }

    public class CastViewHolder extends RecyclerView.ViewHolder {
        ImageView ivCastProfile;
        TextView tvCastName;
        TextView tvCastCharacter;

        public CastViewHolder( View itemView) {
            super(itemView);

            ivCastProfile = itemView.findViewById(R.id.iv_cast_profile);
            tvCastName = itemView.findViewById(R.id.tv_cast_name);
            tvCastCharacter = itemView.findViewById(R.id.tv_character_name);

        }
    }
}
