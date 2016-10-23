package com.code.saher.moviesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.code.saher.moviesapp.Fragments.DetailsFragment;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_details,new DetailsFragment())
                .commit();

    }
}
