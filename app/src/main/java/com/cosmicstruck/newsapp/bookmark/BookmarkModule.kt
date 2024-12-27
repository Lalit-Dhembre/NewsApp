package com.cosmicstruck.newsapp.bookmark

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.cosmicstruck.newsapp.bookmark.data.local.dao.BookmarkDao
import com.cosmicstruck.newsapp.bookmark.data.local.database.BookmarkDatabase
import com.cosmicstruck.newsapp.bookmark.domain.usecases.DeleteArticle
import com.cosmicstruck.newsapp.bookmark.domain.usecases.GetArticle
import com.cosmicstruck.newsapp.bookmark.domain.usecases.GetArticles
import com.cosmicstruck.newsapp.bookmark.domain.usecases.NewsUseCases
import com.cosmicstruck.newsapp.bookmark.domain.usecases.UpsertArticle
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BookmarkModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context : Context
    ) : BookmarkDatabase{
        return Room.databaseBuilder(
            context = context,
            klass = BookmarkDatabase::class.java,
            name = "bookmark_db"
        )
            .fallbackToDestructiveMigrationFrom(1)
            .build()
    }

    @Provides
    @Singleton
    fun provideDao(
        bookmarkDatabase: BookmarkDatabase
    ): BookmarkDao = bookmarkDatabase.dao

    @Provides
    @Singleton
    fun provideUsecases(
        dao: BookmarkDao
    ): NewsUseCases = NewsUseCases(
        upsertArticle = UpsertArticle(dao),
        deleteArticle = DeleteArticle(dao),
        getArticles = GetArticles(dao),
        getArticle = GetArticle(dao)
    )
}