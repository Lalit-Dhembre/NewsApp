package com.cosmicstruck.newsapp.onboarding.di

import android.content.Context
import com.cosmicstruck.newsapp.NewsApplication
import com.cosmicstruck.newsapp.onboarding.data.DatastoreRepository
import com.cosmicstruck.newsapp.onboarding.domain.repository.LocalUserManager
import com.cosmicstruck.newsapp.onboarding.domain.usecases.GetAppEntry
import com.cosmicstruck.newsapp.onboarding.domain.usecases.OnBoardingUseCases
import com.cosmicstruck.newsapp.onboarding.domain.usecases.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object OnBoardingDI {

    @Provides
    @Singleton
    fun provideLocalManager(
        @ApplicationContext context: Context
    ): LocalUserManager = DatastoreRepository(context)

    @Provides
    @Singleton
    fun provideUseCases(
        localUserManager: LocalUserManager
    ) = OnBoardingUseCases(
        saveAppEntry = SaveAppEntry(localUserManager = localUserManager),
        getAppEntry = GetAppEntry(localUserManager = localUserManager)
    )
}