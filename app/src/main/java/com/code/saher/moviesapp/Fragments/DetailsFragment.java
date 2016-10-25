package com.code.saher.moviesapp.Fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.code.saher.moviesapp.Adapter.TrailerAdapter;
import com.code.saher.moviesapp.Interface.Retrofitinterface;
import com.code.saher.moviesapp.Model.Result;
import com.code.saher.moviesapp.ModelForTrailer.Video;
import com.code.saher.moviesapp.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class DetailsFragment extends Fragment {

    final static String MOVIES_BASE_URL = "https://api.themoviedb.org/";
    final static String IMAGE_API_URL="http://image.tmdb.org/t/p/w500";
    Result result;
    ImageView iv_poster;
    TextView tv_1,tv_2,tv_3,tv_4;
    RecyclerView list_trailers;
    TrailerAdapter trailerAdapter;
    ArrayList<com.code.saher.moviesapp.ModelForTrailer.Result> results = new ArrayList<>();
    Gson gson;
    Retrofit retrofit;
    Retrofitinterface moviesAPI;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        result = (Result) getActivity().getIntent().getExtras().getSerializable("result");

        iv_poster= (ImageView) view.findViewById(R.id.item_image);
        tv_1= (TextView) view.findViewById(R.id.item_text1);
        tv_2= (TextView) view.findViewById(R.id.item_text2);
        tv_3= (TextView) view.findViewById(R.id.item_text3);
        tv_4= (TextView) view.findViewById(R.id.item_text4);
        list_trailers = (RecyclerView) view.findViewById(R.id.list_trailers);
        list_trailers.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        list_trailers.setLayoutManager( layoutManager);
        initiaretrofit();

        Picasso.with(getActivity()).load(IMAGE_API_URL+result.getPosterPath()).into(iv_poster);
        tv_1.setText(result.getOriginalTitle());
        tv_2.setText("ID : "+result.getId().toString());
        tv_3.setText("Overview : "+result.getOverview());
        tv_4.setText("Votecount : "+result.getVoteCount().toString());

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
        Call<Video> call = moviesreq.getVideos(result.getId().toString());
        call.enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response) {
                int code = response.code();
                if(code == 200){
                    Video videoResp = response.body();
                    ArrayList<com.code.saher.moviesapp.ModelForTrailer.Result> arrayList= (ArrayList<com.code.saher.moviesapp.ModelForTrailer.Result>) videoResp.getResults();
                    trailerAdapter = new TrailerAdapter(getActivity(),arrayList,R.layout.trailer_cell);
                    list_trailers.setAdapter(trailerAdapter);
                }
                else {
                    Toast.makeText(getActivity(), "Did not work after res " + String.valueOf(code), Toast.LENGTH_LONG).show();}
            }

            @Override
            public void onFailure(Call<Video> call, Throwable t) {
                Toast.makeText(getActivity(), "Did not work: " + String.valueOf(t), Toast.LENGTH_LONG).show();
                Log.e("sunshine","error is " + String.valueOf(t));
            }
        });
    }

}
