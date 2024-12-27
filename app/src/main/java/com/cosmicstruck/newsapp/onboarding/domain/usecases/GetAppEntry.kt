package com.cosmicstruck.newsapp.onboarding.domain.usecases

import android.util.Log
import com.cosmicstruck.newsapp.onboarding.domain.repository.LocalUserManager
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAppEntry @Inject constructor(
    private val localUserManager: LocalUserManager
) {
    operator fun invoke() : Flow<Boolean>{
        return localUserManager.readAppEntry()
    }
}