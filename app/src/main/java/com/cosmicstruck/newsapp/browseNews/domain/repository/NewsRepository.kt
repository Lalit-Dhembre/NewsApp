package com.cosmicstruck.newsapp.browseNews.domain.repository

import androidx.paging.PagingData
import com.cosmicstruck.newsapp.browseNews.data.remote.dto.news.Data
import com.cosmicstruck.newsapp.browseNews.data.remote.dto.news.newsdto
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getNews() : Flow<PagingData<Data>>
}