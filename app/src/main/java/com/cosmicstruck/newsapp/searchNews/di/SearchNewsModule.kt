package com.cosmicstruck.newsapp.searchNews.di

import com.cosmicstruck.newsapp.searchNews.data.remote.service.SearchNewsApiService
import com.cosmicstruck.newsapp.searchNews.data.repository.SearchedNewsImpl
import com.cosmicstruck.newsapp.searchNews.domain.repository.SearchNewsRepository
import com.cosmicstruck.newsapp.searchNews.domain.usecaase.SearchNewsUseCase
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
object SearchNewsModule {

    @Provides
    @Singleton
    fun provideSearchNewsApiService() : SearchNewsApiService = Retrofit
        .Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SearchNewsApiService::class.java)

    @Provides
    @Singleton
    fun provideSearchNewsRepository(
        searchNewsApiService: SearchNewsApiService
    ) : SearchNewsRepository = SearchedNewsImpl(
        searchNewsApiService
    )

    @Provides
    @Singleton
    fun provideUseCase(
        searchNewsRepository: SearchNewsRepository
    ): SearchNewsUseCase = SearchNewsUseCase(searchNewsRepository = searchNewsRepository)
}