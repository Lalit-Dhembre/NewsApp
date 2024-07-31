package com.example.movieratingsapp.API

import com.example.movieratingsapp.Constants.Companion.API_KEY
import com.example.movieratingsapp.Model.Response1
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Newsapi {
    @GET("v2/top-headlines")
    suspend fun getHeadlines(
        @Query("country") countryCode: String = "us",
        @Query("page") pageNo: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY
    ): retrofit2.Response<com.example.movieratingsapp.Model.Response1>

    @GET("v2/everything")
    suspend fun getSearch(
        @Query("q") searchQuery: String,
        @Query("page") pageNo: Int = 1,
        @Query("apiKey") apiKey: String = API_KEY
    ): retrofit2.Response<com.example.movieratingsapp.Model.Response1>
}
