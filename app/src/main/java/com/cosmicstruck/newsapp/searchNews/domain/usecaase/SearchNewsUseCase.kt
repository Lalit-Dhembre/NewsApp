package com.cosmicstruck.newsapp.searchNews.domain.usecaase

import androidx.paging.PagingData
import com.cosmicstruck.newsapp.browseNews.data.remote.dto.news.Data
import com.cosmicstruck.newsapp.searchNews.domain.repository.SearchNewsRepository
import kotlinx.coroutines.flow.Flow

class SearchNewsUseCase(
    private val searchNewsRepository: SearchNewsRepository
) {

    operator fun invoke(
        keyword : String
    ): Flow<PagingData<Data>>{
        return searchNewsRepository.getSearchedNews(keyword)
    }
}