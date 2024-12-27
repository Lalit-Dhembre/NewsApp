package com.cosmicstruck.newsapp.bookmark.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cosmicstruck.newsapp.bookmark.data.local.dao.BookmarkDao
import com.cosmicstruck.newsapp.bookmark.data.local.entity.BookmarkArticles

@Database(entities = [BookmarkArticles::class], version = 2, exportSchema = false)
abstract class BookmarkDatabase: RoomDatabase() {
    abstract val dao : BookmarkDao
}