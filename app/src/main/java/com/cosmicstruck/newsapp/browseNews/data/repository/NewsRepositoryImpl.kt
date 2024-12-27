package com.cosmicstruck.newsapp.browseNews.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.cosmicstruck.newsapp.browseNews.data.paging.NewsPagingSource
import com.cosmicstruck.newsapp.browseNews.data.remote.dto.news.Data
import com.cosmicstruck.newsapp.browseNews.data.remote.service.BrowseNewsService
import com.cosmicstruck.newsapp.browseNews.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class NewsRepositoryImpl(
    private val newsService: BrowseNewsService
): NewsRepository {
    override fun getNews(

    ): Flow<PagingData<Data>> {
        return Pager(
            config = PagingConfig(pageSize = 25),
            pagingSourceFactory = {
                NewsPagingSource(
                    newsService = newsService
                )
            }
        ).flow
    }
}