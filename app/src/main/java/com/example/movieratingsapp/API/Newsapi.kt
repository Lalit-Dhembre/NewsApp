package com.example.movieratingsapp.API

import com.example.movieratingsapp.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.Query
import retrofit2.http.GET

interface Newsapi {
    @GET("v2/top-headlines")
    suspend fun getHeadlines(
        @Query("country")
        countryCode: String = "in",
        @Query("page")
        pageNo: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<Newsapi>
    @GET("v2/top-headlines")
    suspend fun getSearch(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNo: Int = 1,
        @Query("apiKey")
        apiKey: String = API_KEY
    ): Response<Newsapi>


}