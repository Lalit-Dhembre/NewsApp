package com.cosmicstruck.newsapp.browseNews.presentation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.cosmicstruck.newsapp.bookmark.data.local.entity.BookmarkArticles
import com.cosmicstruck.newsapp.bookmark.domain.usecases.NewsUseCases
import com.cosmicstruck.newsapp.browseNews.data.remote.dto.news.Data
import com.cosmicstruck.newsapp.browseNews.domain.usecases.BrowseUseCases
import com.cosmicstruck.newsapp.browseNews.domain.usecases.GetNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BrowseNewsViewModel @Inject constructor(
    private val browseUseCases: BrowseUseCases,
    private val newsUseCases: NewsUseCases
): ViewModel() {

    val news = browseUseCases.getNewsUseCase().cachedIn(viewModelScope)

    fun addBookmark(article : Data){
        Log.d("BOOKMARK ADDED","FROM VIEWMODEL")
        viewModelScope.launch{
            newsUseCases.upsertArticle.invoke(
                BookmarkArticles(
                    category = article.category,
                    country = article.country,
                    description = article.description,
                    image = article.image,
                    language = article.language,
                    published_at = article.published_at,
                    source = article.source,
                    title = article.title,
                    url = article.url
                )
            )
        }
    }

}