package com.code.saher.moviesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.Menu;
import android.view.MenuItem;

import com.code.saher.moviesapp.Adapter.FavoriteAdapter;
import com.code.saher.moviesapp.Models.FavoriteModel;
import com.code.saher.moviesapp.MoviesDB.DataBaseManager;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteActivity extends AppCompatActivity {

    @BindView(R.id.rv_favorite) RecyclerView rv_favorite;
    ArrayList<FavoriteModel> movies;
    DataBaseManager dataBaseManager;
    FavoriteAdapter favoriteAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        ButterKnife.bind(this);
        SpannableString title = new SpannableString("Favourites");
        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle(title);
        }
        dataBaseManager = new DataBaseManager(this);

        rv_favorite.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        rv_favorite.setLayoutManager( layoutManager);
        movies = dataBaseManager.viewdata();
        favoriteAdapter= new FavoriteAdapter(this,movies,R.layout.favoriterow);
        rv_favorite.setAdapter(favoriteAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.onBackPressed();
                break;
        }
        return true;
    }
}
