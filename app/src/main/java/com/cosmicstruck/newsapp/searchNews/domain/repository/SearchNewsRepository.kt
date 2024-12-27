package com.cosmicstruck.newsapp.searchNews.domain.repository

import androidx.paging.PagingData
import com.cosmicstruck.newsapp.browseNews.data.remote.dto.news.Data
import kotlinx.coroutines.flow.Flow

interface SearchNewsRepository {

    fun getSearchedNews(
        keyword : String
    ) : Flow<PagingData<Data>>
}