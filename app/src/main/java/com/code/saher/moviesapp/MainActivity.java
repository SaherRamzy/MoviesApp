package com.code.saher.moviesapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.code.saher.moviesapp.Adapter.RetrofitAdapter;
import com.code.saher.moviesapp.Fragments.DetailsFragment;
import com.code.saher.moviesapp.Models.Model.Result;

//public class MainActivity extends AppCompatActivity implements MainFragment.Interface {
public class MainActivity extends AppCompatActivity implements RetrofitAdapter.Interface {

    Boolean inTwoPane = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        validation(savedInstanceState);

    }

    @Override
    public void onSelectionchange(Result data) {
        if(inTwoPane){
            Bundle arguments = new Bundle();
            arguments.putSerializable("result", data);
            DetailsFragment detailsFragment = new DetailsFragment();
            detailsFragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.description_fragment, detailsFragment)
                    .commit();
        }
        else {
            Intent intent = new Intent(this,DetailsActivity.class);
            intent.putExtra("result", data);
            startActivity(intent);
        }
    }

    public void validation(Bundle savedInstanceState){
        if (isNetworkAvailable()) {
            if(null != findViewById(R.id.description_fragment)){
                inTwoPane=true;
                if (savedInstanceState == null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.description_fragment, new DetailsFragment())
                            .commit();
                }else {
                    inTwoPane = false;
                }
            }
        }
        else {
            finish();
            startActivity(new Intent(this, FavoriteActivity.class));
            Toast.makeText(getApplicationContext(), " please turn on the Internet to view movies", Toast.LENGTH_SHORT).show();
        }
    }

    /*This Method to check the network connected or not*/
    public boolean isNetworkAvailable() {
        final ConnectivityManager connectivityManager = ((ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE));
        return connectivityManager.getActiveNetworkInfo() != null &&
                connectivityManager.getActiveNetworkInfo().isConnected();
    }
}