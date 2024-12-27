package com.cosmicstruck.newsapp.bookmark.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cosmicstruck.newsapp.bookmark.data.local.entity.BookmarkArticles
import com.cosmicstruck.newsapp.browseNews.data.remote.dto.news.Data
import kotlinx.coroutines.flow.Flow

@Dao
interface BookmarkDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(article: BookmarkArticles)

    @Delete
    suspend fun delete(article: BookmarkArticles)

    @Query("SELECT * FROM bookmarkarticles")
    fun getArticles(): Flow<List<Data>>

    @Query("SELECT * FROM bookmarkarticles WHERE url=:url")
    suspend fun getArticle(url: String): BookmarkArticles?
}