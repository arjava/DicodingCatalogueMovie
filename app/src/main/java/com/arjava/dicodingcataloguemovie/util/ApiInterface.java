package com.arjava.dicodingcataloguemovie.util;

import com.arjava.dicodingcataloguemovie.BuildConfig;
import com.arjava.dicodingcataloguemovie.model.MovieItems;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by arjava on 11/15/17.
 */

public interface ApiInterface {

//    @GET("/3/search/movie?api_key=<API KEY ANDA>&language=en-US&query=<INPUTAN NAMA MOVIE>")
    @GET("/3/search/movie?api_key="+ BuildConfig.API_KEY +"&language=en-US&")
    Call<MovieItems> getMovieItems (
            @Query("query") String name_movie
    );
    @GET("/3/movie/upcoming?api_key="+ BuildConfig.API_KEY+ "&language=en-US")
    Call<MovieItems> getMovieUpcoming (
    );
}
