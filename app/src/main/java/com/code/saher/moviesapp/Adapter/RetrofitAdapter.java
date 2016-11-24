package com.code.saher.moviesapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.code.saher.moviesapp.Models.Model.Result;
import com.code.saher.moviesapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by saher on 9/4/2016.
 */
public class RetrofitAdapter extends RecyclerView.Adapter<RetrofitAdapter.MovieViewHolder> {
    private final static String IMAGE_API_URL="http://image.tmdb.org/t/p/w500";
    private ArrayList<Result> data = new ArrayList<>();
    private int rowLayout;
    Context context;

    public RetrofitAdapter(Context context,ArrayList<Result> data, int rowLayout) {
        this.context = context;
        this.data = data;
        this.rowLayout = rowLayout;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieViewHolder holder, final int position) {
        holder.tv1.setText(data.get(position).getOriginalTitle());
        Picasso.with(context).load(IMAGE_API_URL+data.get(position).getPosterPath()).into(holder.iv_showIcon);
        holder.iv_showIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Interface) context).onSelectionchange(data.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder{
        TextView tv1;
        ImageView iv_showIcon;

        public MovieViewHolder(View itemView) {
            super(itemView);
            tv1 = (TextView) itemView.findViewById(R.id.item_text0);
            iv_showIcon = (ImageView) itemView.findViewById(R.id.item_image);
        }
    }

    public interface Interface {
        void onSelectionchange(Result data);
    }
}
