package com.cosmicstruck.newsapp.browseNews.data.remote.service

import com.cosmicstruck.newsapp.browseNews.data.remote.dto.news.newsdto
import com.cosmicstruck.newsapp.utils.API_KEY
import kotlinx.coroutines.flow.Flow
import retrofit2.http.Query
import retrofit2.http.GET



interface BrowseNewsService {

    @GET("news")
    suspend fun getNews(
        @Query("access_key") access_key : String = API_KEY,
        @Query("offset") offset : Int,
        @Query("countries") countries : String = "in"
    ): newsdto




}