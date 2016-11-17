package com.code.saher.moviesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.code.saher.moviesapp.Adapter.FavoriteAdapter;
import com.code.saher.moviesapp.MoviesDB.DataBaseManager;
import com.code.saher.moviesapp.Models.FavoriteModel;

import java.util.ArrayList;

public class FavoriteActivity extends AppCompatActivity {
    RecyclerView rv_favorite;
    ArrayList<FavoriteModel> movies;
    DataBaseManager dataBaseManager;
    FavoriteAdapter favoriteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        dataBaseManager = new DataBaseManager(this);
        rv_favorite = (RecyclerView)findViewById(R.id.rv_favorite);
        rv_favorite.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rv_favorite.setLayoutManager( layoutManager);
        movies = dataBaseManager.viewdata();
        favoriteAdapter= new FavoriteAdapter(this,movies,R.layout.favoriterow);
        rv_favorite.setAdapter(favoriteAdapter);

    }
}
