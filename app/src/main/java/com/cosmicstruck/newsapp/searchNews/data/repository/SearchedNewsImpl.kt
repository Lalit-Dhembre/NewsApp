package com.cosmicstruck.newsapp.searchNews.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cosmicstruck.newsapp.browseNews.data.remote.dto.news.Data
import com.cosmicstruck.newsapp.searchNews.data.paging.SearchNewsPagingSource
import com.cosmicstruck.newsapp.searchNews.data.remote.service.SearchNewsApiService
import com.cosmicstruck.newsapp.searchNews.domain.repository.SearchNewsRepository
import kotlinx.coroutines.flow.Flow

class SearchedNewsImpl(
    private val getSearchNewsApiService: SearchNewsApiService
): SearchNewsRepository {
    override fun getSearchedNews(
        keyword : String
    ): Flow<PagingData<Data>> {
        return Pager(
            config = PagingConfig(pageSize = 25),
            pagingSourceFactory = {
                SearchNewsPagingSource(
                    newsService = getSearchNewsApiService,
                    keywords = keyword
                )
            }
        ).flow
    }

}