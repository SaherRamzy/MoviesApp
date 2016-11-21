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

        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("result", getIntent().getExtras().getSerializable("result"));
            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(bundle);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.activity_details, detailsFragment)
                    .commit();
        }

    }
}
