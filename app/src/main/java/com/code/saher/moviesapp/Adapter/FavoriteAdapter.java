package com.code.saher.moviesapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.code.saher.moviesapp.Models.FavoriteModel;
import com.code.saher.moviesapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by saher on 11/7/2016.
 */

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder> {

    private final static String IMAGE_API_URL="http://image.tmdb.org/t/p/w500";
    private ArrayList<FavoriteModel> data;
    private int rowLayout;
    Context context;

    public FavoriteAdapter(Context context, ArrayList<FavoriteModel> data, int rowLayout) {
        this.context = context;
        this.data = data;
        this.rowLayout = rowLayout;
    }

    @Override
    public FavoriteAdapter.FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new FavoriteAdapter.FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteAdapter.FavoriteViewHolder holder, int position) {
        holder.Name.setText(data.get(position).Name);
        holder.Overview.setText(data.get(position).Overview);
        Picasso.with(context).load(IMAGE_API_URL+data.get(position).Logo).into(holder.logo);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.movie_name)TextView Name;
        @BindView(R.id.movie_overview)TextView Overview;
        @BindView(R.id.fav_logo)ImageView logo;

        public FavoriteViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
       }
    }
}
