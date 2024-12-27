package com.cosmicstruck.newsapp.searchNews.data.remote.service

import com.cosmicstruck.newsapp.browseNews.data.remote.dto.news.newsdto
import com.cosmicstruck.newsapp.utils.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface SearchNewsApiService {
    @GET("news")
    suspend fun getSearchedNews(
        @Query("access_key") access_key : String = API_KEY,
        @Query("offset") offset : Int,
        @Query("countries") countries : String = "in",
        @Query("keywords") keyword : String) : newsdto
}