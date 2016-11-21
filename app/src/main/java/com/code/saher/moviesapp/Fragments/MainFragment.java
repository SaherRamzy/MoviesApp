package com.code.saher.moviesapp.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Toast;

import com.code.saher.moviesapp.FavoriteActivity;
import com.code.saher.moviesapp.Interface.RetrofitInterface;
import com.code.saher.moviesapp.Interface.onItemClickListenercustom;
import com.code.saher.moviesapp.Models.Model.Movie;
import com.code.saher.moviesapp.Models.Model.Result;
import com.code.saher.moviesapp.R;
import com.code.saher.moviesapp.Adapter.RetrofitAdapter1;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainFragment extends Fragment {

    RetrofitInterface moviesAPI;
//    RetrofitAdapter retrofitAdapter;
    RetrofitAdapter1 retrofitAdapter1;
    RecyclerView rv_show;
    Gson gson;
    Retrofit retrofit;
    ProgressDialog progressDialog;

    public MainFragment() {
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_main, container, false);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please Wait ... ");

        rv_show = (RecyclerView) view.findViewById(R.id.rv_show);
        rv_show.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getActivity(),2);
        rv_show.setLayoutManager( layoutManager);

        progressDialog.show();
        //init retrofit
        initRetrofit();
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

    public interface Interface {
        void onSelectionchange(Result data);
    }

    public void getDataFromAPI(RetrofitInterface moviesreq){
        Call<Movie> call = moviesreq.getData();
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                int code = response.code();
                if(code == 200){
                    progressDialog.dismiss();
                    Movie moviesResp = response.body();
                    ArrayList<Result> arrayList= (ArrayList<Result>) moviesResp.getResults();
//                    retrofitAdapter = new RetrofitAdapter(getActivity(), arrayList, R.layout.grid_row);
                    retrofitAdapter1 = new RetrofitAdapter1(getActivity(), arrayList, R.layout.grid_row, new onItemClickListenercustom() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        }

                        @Override
                        public void onItemClick(Result result) {
//                            Intent intent = new Intent(getActivity(),DetailsActivity.class);
//                            intent.putExtra("result", result);
//                            getActivity().startActivity(intent);
                            ((Interface) getActivity()).onSelectionchange(result);
                        }
                    });
                    rv_show.setAdapter(retrofitAdapter1);
                }
                else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.msg_not_response) + String.valueOf(code), Toast.LENGTH_LONG).show();}
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(getActivity(), getResources().getString(R.string.msg_failure), Toast.LENGTH_LONG).show();
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
        switch(item.getItemId()){
            case R.id.most_popular:
                progressDialog.show();
                getDataFromPopularAPI(moviesAPI);
                break;
            case R.id.highest_rated:
                progressDialog.show();
                getDataFromTopRatedAPI(moviesAPI);
                break;
            case R.id.favorite:
                startActivity(new Intent(getActivity(), FavoriteActivity.class));
                break;
        }
        return true;
    }

    public void getDataFromPopularAPI(RetrofitInterface moviesreq){
        Call<Movie> call = moviesreq.getPopularData();
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                int code = response.code();
                if(code == 200){
                    progressDialog.dismiss();
                    Movie moviesResp = response.body();
                    ArrayList<Result> arrayList= (ArrayList<Result>) moviesResp.getResults();
//                    retrofitAdapter = new RetrofitAdapter(getActivity(),arrayList,R.layout.grid_row);
                    retrofitAdapter1 = new RetrofitAdapter1(getActivity(), arrayList, R.layout.grid_row, new onItemClickListenercustom() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        }

                        @Override
                        public void onItemClick(Result result) {
//                            Intent intent = new Intent(getActivity(),DetailsActivity.class);
//                            intent.putExtra("result", result);
//                            getActivity().startActivity(intent);
                            ((Interface) getActivity()).onSelectionchange(result);
                        }
                    });
                    rv_show.setAdapter(retrofitAdapter1);
                }
                else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.msg_not_response), Toast.LENGTH_LONG).show();}
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(getActivity(), getResources().getString(R.string.msg_failure), Toast.LENGTH_LONG).show();
            }
        });
    }

    public void getDataFromTopRatedAPI(RetrofitInterface moviesreq){
        Call<Movie> call = moviesreq.getTopRatedData();
        call.enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {
                int code = response.code();
                if(code == 200){
                    progressDialog.dismiss();
                    Movie moviesResp = response.body();
                    ArrayList<Result> arrayList= (ArrayList<Result>) moviesResp.getResults();
//                    retrofitAdapter = new RetrofitAdapter(getActivity(),arrayList,R.layout.grid_row);
                    retrofitAdapter1 = new RetrofitAdapter1(getActivity(), arrayList, R.layout.grid_row, new onItemClickListenercustom() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        }

                        @Override
                        public void onItemClick(Result result) {
//                            Intent intent = new Intent(getActivity(),DetailsActivity.class);
//                            intent.putExtra("result", result);
//                            getActivity().startActivity(intent);
                            ((Interface) getActivity()).onSelectionchange(result);
                        }
                    });
                    rv_show.setAdapter(retrofitAdapter1);
                }
                else {
                    Toast.makeText(getActivity(), getResources().getString(R.string.msg_not_response), Toast.LENGTH_LONG).show();}
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(getActivity(), getResources().getString(R.string.msg_failure), Toast.LENGTH_LONG).show();
            }
        });
    }
}
