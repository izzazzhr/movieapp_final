package com.example.movieapp_final.ui.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieapp_final.R;
import com.example.movieapp_final.data.models.Trailer;

import java.util.List;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder>{

    private List<Trailer> trailerList;
    private Context mContext;

    public TrailerAdapter(Context context, List<Trailer> trailerList){
        this.mContext = context;
        this.trailerList = trailerList;
    }





    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_list, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      final Trailer data = trailerList.get(position);

      holder.btnTrailer.setText(data.getType());
      holder.btnTrailer.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              Intent intent = new Intent(Intent.ACTION_VIEW);
              intent.setData(Uri.parse("https://www.youtube.com/watch?v=" + data.getKey()));
              mContext.startActivity(intent);
          }
      });
    }


    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button btnTrailer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            btnTrailer = itemView.findViewById(R.id.btn_watch);

        }


    }
}
