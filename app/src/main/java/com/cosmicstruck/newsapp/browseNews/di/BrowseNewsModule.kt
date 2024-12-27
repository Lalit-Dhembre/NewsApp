package com.cosmicstruck.newsapp.browseNews.di

import com.cosmicstruck.newsapp.browseNews.data.remote.service.BrowseNewsService
import com.cosmicstruck.newsapp.browseNews.data.repository.NewsRepositoryImpl
import com.cosmicstruck.newsapp.browseNews.domain.repository.NewsRepository
import com.cosmicstruck.newsapp.browseNews.domain.usecases.BrowseUseCases
import com.cosmicstruck.newsapp.browseNews.domain.usecases.GetNewsUseCase
import com.cosmicstruck.newsapp.utils.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BrowseNewsModule {

    @Provides
    @Singleton
    fun provideBrowserService() : BrowseNewsService = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(BrowseNewsService::class.java)

    @Provides
    @Singleton
    fun provideBrowseNewsRepository(
        browseNewsService: BrowseNewsService
    ) : NewsRepository = NewsRepositoryImpl(
        browseNewsService
    )

    @Provides
    @Singleton
    fun provideBrowseNewsUseCase(
        newsRepository: NewsRepository
    ) : BrowseUseCases = BrowseUseCases(
        getNewsUseCase = GetNewsUseCase(newsRepository),
    )
}