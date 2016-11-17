package com.code.saher.moviesapp.Fragments;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.code.saher.moviesapp.Adapter.ReviewAdapter;
import com.code.saher.moviesapp.Adapter.TrailerAdapter;
import com.code.saher.moviesapp.Interface.RetrofitInterface;
import com.code.saher.moviesapp.Models.Model.Result;
import com.code.saher.moviesapp.Models.ModelForReview.Review;
import com.code.saher.moviesapp.Models.ModelForTrailer.Video;
import com.code.saher.moviesapp.MoviesDB.DataBaseManager;
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


public class DetailsFragment extends Fragment implements View.OnClickListener{

    Result result;
    ImageView iv_poster;
    TextView tv_1,tv_2,tv_3,tv_4;
    Button btn_fav;
    RecyclerView list_trailers,list_reviews;
    TrailerAdapter trailerAdapter;
    ReviewAdapter reviewAdapter;
    Gson gson;
    Retrofit retrofit;
    RetrofitInterface moviesAPI;
    DataBaseManager dataBaseManager;
    ArrayList<com.code.saher.moviesapp.Models.ModelForTrailer.Result> Trailer_arrayList;
    ArrayList<com.code.saher.moviesapp.Models.ModelForReview.Result> Review_arrayList;
    SQLiteDatabase DB;
    Cursor cursor;

    public DetailsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        dataBaseManager = new DataBaseManager(getActivity());
        cursor=null;
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
        btn_fav = (Button) view.findViewById(R.id.btn_fav);
        btn_fav.setOnClickListener(this);

        list_trailers = (RecyclerView) view.findViewById(R.id.list_trailers);
        list_reviews = (RecyclerView) view.findViewById(R.id.list_reviews);
        list_trailers.setHasFixedSize(true);
        list_reviews.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        list_trailers.setLayoutManager( layoutManager);
        list_reviews.setLayoutManager(layoutManager1);
        initRetrofit();

        Picasso.with(getActivity()).load(getResources().getString(R.string.IMAGE_API_URL)+result.getPosterPath()).into(iv_poster);
        tv_1.setText(result.getOriginalTitle());
        tv_2.setText("ID : "+result.getId());
        tv_3.setText("Overview : "+result.getOverview());
        tv_4.setText("Votecount : "+result.getVoteCount().toString());

        return view;
    }

    private void initRetrofit(){
        gson = new GsonBuilder().create();
        retrofit = new Retrofit.Builder()
                .baseUrl(getResources().getString(R.string.MOVIES_BASE_URL))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        moviesAPI = retrofit.create(RetrofitInterface.class);
        getDataFromAPI(moviesAPI);
    }

    public void getDataFromAPI(RetrofitInterface moviesreq){
        Call<Video> call = moviesreq.getVideos(result.getId());
        call.enqueue(new Callback<Video>() {
            @Override
            public void onResponse(Call<Video> call, Response<Video> response) {
                int code = response.code();
                if(code == 200){
                    Video videoResp = response.body();
                    Trailer_arrayList = (ArrayList<com.code.saher.moviesapp.Models.ModelForTrailer.Result>) videoResp.getResults();
                    trailerAdapter = new TrailerAdapter(getActivity(), Trailer_arrayList,R.layout.trailer_cell);
                    list_trailers.setAdapter(trailerAdapter);
                }
                else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.msg_not_response), Toast.LENGTH_LONG).show();}
            }

            @Override
            public void onFailure(Call<Video> call, Throwable t) {
                Toast.makeText(getActivity(), getResources().getString(R.string.msg_failure), Toast.LENGTH_LONG).show();
            }
        });
        Call<Review> call1 = moviesreq.getReviews(result.getId());
        call1.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                int code=response.code();
                if (code==200){
                    Review reviewResp =response.body();
                    Review_arrayList = (ArrayList<com.code.saher.moviesapp.Models.ModelForReview.Result>) reviewResp.getResults();
                    reviewAdapter = new ReviewAdapter(getActivity(), Review_arrayList,R.layout.review_cell);
                    list_reviews.setAdapter(reviewAdapter);
                }
                else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.msg_not_response), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                Toast.makeText(getActivity(), getResources().getString(R.string.msg_failure), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_fav :
                if(isChecked(result.getId())) {
                    Toast.makeText(getActivity(), getResources().getString(R.string.msg_saved_before), Toast.LENGTH_SHORT).show();
                }
                else {
                    boolean check = dataBaseManager.
                            Add_Movie(result.getId(), result.getOriginalTitle(), result.getOverview());
                    if (check) Toast.makeText(getActivity(), getResources().getString(R.string.msg_saved), Toast.LENGTH_SHORT).show();
                    else Toast.makeText(getActivity(), getResources().getString(R.string.msg_not_saved), Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
        }
        return true;
    }

    public boolean isChecked(String searchID){
        String[] col = {DataBaseManager.col_1};
        String selection = DataBaseManager.col_1 +"=?";
        String[] selectionArg = {searchID};
        String limit = "1";
        cursor = dataBaseManager.getReadableDatabase().query(DataBaseManager.table_name,col,selection,selectionArg,null,null,null,limit);
        boolean exist = (cursor.getCount()>0);
        cursor.close();
        return exist;
    }
}
