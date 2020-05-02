package com.example.cocktailapp;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface CocktailApi {

    @GET("search.php")
    Call<JsonObject> getCocktails(@Query("s") String s);
}
