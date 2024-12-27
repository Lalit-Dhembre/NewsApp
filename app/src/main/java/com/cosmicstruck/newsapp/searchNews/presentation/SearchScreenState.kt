package com.cosmicstruck.newsapp.searchNews.presentation

import androidx.paging.PagingData
import com.cosmicstruck.newsapp.browseNews.data.remote.dto.news.Data
import kotlinx.coroutines.flow.Flow

data class SearchScreenState(
    val searchQuery: String = "",
    val articles: Flow<PagingData<Data>>? = null
)
