package com.cosmicstruck.newsapp.browseNews.domain.usecases

import android.util.Log
import androidx.paging.PagingData
import com.cosmicstruck.newsapp.browseNews.data.remote.dto.news.Data
import com.cosmicstruck.newsapp.browseNews.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNewsUseCase(
    private val newsRepository: NewsRepository
) {
    operator fun invoke() : Flow<PagingData<Data>>{
        return newsRepository.getNews()
    }
}