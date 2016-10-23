package com.code.saher.moviesapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.code.saher.moviesapp.DetailsActivity;
import com.code.saher.moviesapp.Adapter.RetrofitAdapter;
import com.code.saher.moviesapp.Interface.Retrofitinterface;
import com.code.saher.moviesapp.Interface.onItemClickListenercustom;
import com.code.saher.moviesapp.Model.Movie;
import com.code.saher.moviesapp.Model.Result;
import com.code.saher.moviesapp.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainFragment extends Fragment {

    final static String MOVIES_BASE_URL = "https://api.themoviedb.org/";
    Retrofitinterface moviesAPI;
    RetrofitAdapter retrofitAdapter;
    RecyclerView rv_show;
    Gson gson;
    Retrofit retrofit;

    public MainFragment() {
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main, container, false);
        try {
            ViewConfiguration config = ViewConfiguration.get(getActivity());
            Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
            if(menuKeyField != null) {
                menuKeyField.setAccessible(true);
                menuKeyField.setBoolean(config, false);
            }
        } catch (Exception ex) {}

        rv_show = (RecyclerView) view.findViewById(R.id.rv_show);
        rv_show.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        rv_show.setLayoutManager( layoutManager);
        //init retrofit
        initiaretrofit();
        return view;
    }
    private void initiaretrofit(){
        gson = new GsonBuilder().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(MOVIES_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        moviesAPI = retrofit.create(Retrofitinterface.class);
        getDataFromAPI(moviesAPI);
    }

    public void getDataFromAPI(Retrofitinterface moviesreq){
        Call<Movie> call = moviesreq.getData();
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                int code = response.code();
                if(code == 200){
                    Movie moviesResp = response.body();
                    ArrayList<Result> arrayList= (ArrayList<Result>) moviesResp.getResults();
                    retrofitAdapter = new RetrofitAdapter(getActivity(), arrayList, R.layout.grid_row, new onItemClickListenercustom() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        }

                        @Override
                        public void onItemClick(Result result) {
                            Intent intent = new Intent(getActivity(),DetailsActivity.class);
                            intent.putExtra("result", result);
                            getActivity().startActivity(intent);
                        }
                    });
                    rv_show.setAdapter(retrofitAdapter);
                }
                else {
                    Toast.makeText(getActivity(), "Did not work after res " + String.valueOf(code), Toast.LENGTH_LONG).show();}
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(getActivity(), "Did not work: " + String.valueOf(t), Toast.LENGTH_LONG).show();
                Log.e("sunshine","error is " + String.valueOf(t));
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        inflater.inflate(R.menu.menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
//        return super.onOptionsItemSelected(item);
        switch(item.getItemId()){
            case R.id.most_popular:
                getDataFromPopularAPI(moviesAPI);
                break;
            case R.id.highest_rated:
                getDataFromTopRatedAPI(moviesAPI);
                break;
        }
        return true;
    }

    public void getDataFromPopularAPI(Retrofitinterface moviesreq){
        Call<Movie> call = moviesreq.getPopularData();
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                int code = response.code();
                if(code == 200){
                    Movie moviesResp = response.body();
                    ArrayList<Result> arrayList= (ArrayList<Result>) moviesResp.getResults();
                    retrofitAdapter = new RetrofitAdapter(getActivity(), arrayList, R.layout.grid_row, new onItemClickListenercustom() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        }

                        @Override
                        public void onItemClick(Result result) {
//                            Toast.makeText(MainActivity.this,"click in"+result.getOriginalTitle().toString(),Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(),DetailsActivity.class);
                            intent.putExtra("result", result);
                            startActivity(intent);
                        }
                    });
                    rv_show.setAdapter(retrofitAdapter);
                }
                else {
                    Toast.makeText(getActivity(), "Did not work after res " + String.valueOf(code), Toast.LENGTH_LONG).show();}
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(getActivity(), "Did not work: " + String.valueOf(t), Toast.LENGTH_LONG).show();
                Log.e("sunshine","error is " + String.valueOf(t));
            }
        });
    }

    public void getDataFromTopRatedAPI(Retrofitinterface moviesreq){
        Call<Movie> call = moviesreq.getTopRatedData();
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                int code = response.code();
                if(code == 200){
                    Movie moviesResp = response.body();
                    ArrayList<Result> arrayList= (ArrayList<Result>) moviesResp.getResults();
                    retrofitAdapter = new RetrofitAdapter(getActivity(), arrayList, R.layout.grid_row, new onItemClickListenercustom() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        }

                        @Override
                        public void onItemClick(Result result) {
//                            Toast.makeText(MainActivity.this,"click in"+result.getOriginalTitle().toString(),Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getActivity(),DetailsActivity.class);
                            intent.putExtra("result", result);
                            startActivity(intent);
                        }
                    });
                    rv_show.setAdapter(retrofitAdapter);
                }
                else {
                    Toast.makeText(getActivity(), "Did not work after res " + String.valueOf(code), Toast.LENGTH_LONG).show();}
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(getActivity(), "Did not work: " + String.valueOf(t), Toast.LENGTH_LONG).show();
                Log.e("sunshine","error is " + String.valueOf(t));
            }
        });
    }
}
