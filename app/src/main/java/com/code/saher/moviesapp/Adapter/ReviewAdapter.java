package com.code.saher.moviesapp.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.code.saher.moviesapp.R;

import java.util.ArrayList;

/**
 * Created by saher on 11/16/2016.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MovieViewHolder>  {

    private ArrayList<com.code.saher.moviesapp.Models.ModelForReview.Result> data;
    private int rowLayout;
    Context context;
    public ReviewAdapter(Context context, ArrayList<com.code.saher.moviesapp.Models.ModelForReview.Result> data, int rowLayout) {
        this.context = context;
        this.data = data;
        this.rowLayout = rowLayout;
    }

    @Override
    public ReviewAdapter.MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(rowLayout, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.MovieViewHolder holder, int position) {
        holder.review_author.setText(data.get(position).getAuthor());
        holder.review_content.setText(data.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
    public static class MovieViewHolder extends RecyclerView.ViewHolder{
        TextView review_author,review_content;

        public MovieViewHolder(View itemView) {
            super(itemView);
            review_author = (TextView) itemView.findViewById(R.id.review_author);
            review_content = (TextView) itemView.findViewById(R.id.review_content);
        }
    }
}
