package com.code.saher.moviesapp.Interface;

import com.code.saher.moviesapp.Models.Model.Movie;
import com.code.saher.moviesapp.Models.ModelForReview.Review;
import com.code.saher.moviesapp.Models.ModelForTrailer.Video;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by saher on 8/11/2016.
 */
public interface RetrofitInterface {

    final static String MOVIES_API_KEY="705bc78ebdaeeaf25de43d695faac9a3";

    @GET("3/discover/movie?api_key="+MOVIES_API_KEY)
    Call<Movie> getData();

    @GET("3/movie/popular?api_key="+MOVIES_API_KEY)
    Call<Movie> getPopularData();

    @GET("3/movie/top_rated?api_key="+MOVIES_API_KEY)
    Call<Movie> getTopRatedData();

    @GET("3/movie/{id}/videos?api_key="+MOVIES_API_KEY)
    Call<Video> getVideos(@Path("id") String id);

    @GET("3/movie/{id}/reviews?api_key="+MOVIES_API_KEY)
    Call<Review> getReviews(@Path("id") String id);

}
