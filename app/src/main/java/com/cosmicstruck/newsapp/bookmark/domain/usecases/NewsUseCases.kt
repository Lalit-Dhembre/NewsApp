package com.cosmicstruck.newsapp.bookmark.domain.usecases

import android.util.Log
import com.cosmicstruck.newsapp.bookmark.data.local.dao.BookmarkDao
import com.cosmicstruck.newsapp.bookmark.data.local.entity.BookmarkArticles
import com.cosmicstruck.newsapp.browseNews.data.remote.dto.news.Data
import com.cosmicstruck.newsapp.browseNews.data.remote.dto.news.toBookmarkArticles
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach

data class NewsUseCases(
    val upsertArticle: UpsertArticle,
    val deleteArticle: DeleteArticle,
    val getArticles: GetArticles,
    val getArticle: GetArticle
)

class UpsertArticle(
    private val newsDao: BookmarkDao
) {

    suspend operator fun invoke(article: BookmarkArticles){
        newsDao.upsert(article = article)
    }

}
class DeleteArticle (
    private val newsDao: BookmarkDao
) {

    suspend operator fun invoke(article: BookmarkArticles){
        newsDao.delete(article = article)
    }

}

class GetArticle (
    private val newsDao: BookmarkDao
) {

    suspend operator fun invoke(url: String): BookmarkArticles?{
        return newsDao.getArticle(url = url)
    }

}


class GetArticles(
    private val newsDao: BookmarkDao
) {

    operator fun invoke(): Flow<List<BookmarkArticles>> {
        Log.d("REACHED IN GA UC","${newsDao.getArticles().toString()}")
        return newsDao.getArticles().map { it.map { it.toBookmarkArticles() } }

    }
}