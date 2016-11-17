package com.code.saher.moviesapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.code.saher.moviesapp.Fragments.MainFragment;

public class MainActivity extends AppCompatActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.activity_main, new MainFragment())
                .commit();
//        if(findViewById(R.id.activity_main)!=null){
//            if(savedInstanceState!=null){
//                return;
//            }
//            MainFragment mainFragment = new MainFragment();
//            mainFragment.setArguments(getIntent().getExtras());
//            getSupportFragmentManager().beginTransaction()
//                .add(R.id.activity_main, mainFragment)
//                .commit();
//        }
//
    }

//    @Override
//    public void onSelectionchange(int position) {
//        Fragment detailsFragment = getFragmentManager().findFragmentById(R.id.description_fragment);
//
//        if(detailsFragment!=null){
//            detailsFragment.set
//        }
//    }
}