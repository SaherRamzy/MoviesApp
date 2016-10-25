package com.code.saher.moviesapp.Interface;

import com.code.saher.moviesapp.Model.Movie;
import com.code.saher.moviesapp.ModelForTrailer.Video;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by saher on 8/11/2016.
 */
public interface Retrofitinterface {

    final static String MOVIES_API_KEY="705bc78ebdaeeaf25de43d695faac9a3";

//    @GET("/users/{user}")
//    Call<Retrofitmodel> getUser(@Path("user") String user);
//
//    @GET("/search/users")
//    Call<Retrofitmodel> getUsersNamedTom(@Query("q") String name);
//
//    @POST("/user/create")
//    Call<Retrofitmodel> createUser(@Body String name);
//
//    @PUT("/user/{id}/update")
//    Call<Item> updateUser(@Path("id") String id , @Body Item user);
//
//    @GET("weather?/cnt=7&units=metric&q={country}&APPID="+APPID)
//    Call<Model> getData(@path("country") String country);

    @GET("3/discover/movie?api_key="+MOVIES_API_KEY)
    Call<Movie> getData();

    @GET("3/movie/popular?api_key="+MOVIES_API_KEY)
    Call<Movie> getPopularData();

    @GET("3/movie/top_rated?api_key="+MOVIES_API_KEY)
    Call<Movie> getTopRatedData();

    @GET("3/movie/{id}/videos?api_key="+MOVIES_API_KEY)
    Call<Video> getVideos(@Path("id") String id);

    @GET("3/movie/{id}/reviews?api_key="+MOVIES_API_KEY)
    Call<Movie> getReviews(@Path("id") String id);

}
