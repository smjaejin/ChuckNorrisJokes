package com.example.seanmclane.chucknorrisjokes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by per6 on 1/23/18.
 */

public interface ChuckNorrisJokesAPI {

    String baseURL = "https://api.chucknorris.io/jokes/";

    @GET("random")
    Call<Joke>  getRandomJoke();
    //is a Call so we get it async-ly

    @GET("search")
    Call<ListOfJokes> searchForJoke(@Query("query") String query);

    @GET("{id}")
    Call<Joke> fromID(@Path("id") String id);
}
