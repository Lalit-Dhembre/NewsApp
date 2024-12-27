package com.cosmicstruck.newsapp.onboarding.domain.repository

import kotlinx.coroutines.flow.Flow

interface LocalUserManager {

    suspend fun saveAppEntry(value: Boolean)

    fun readAppEntry() : Flow<Boolean>
}