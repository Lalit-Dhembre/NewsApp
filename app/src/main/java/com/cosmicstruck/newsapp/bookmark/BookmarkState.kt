package com.cosmicstruck.newsapp.bookmark

import com.cosmicstruck.newsapp.bookmark.data.local.entity.BookmarkArticles
import com.cosmicstruck.newsapp.browseNews.data.remote.dto.news.Data
import kotlinx.coroutines.flow.Flow

data class BookmarkState(
    val articles: List<BookmarkArticles>? = null
)