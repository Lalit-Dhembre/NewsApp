package com.cosmicstruck.newsapp.onboarding.domain.usecases

import com.cosmicstruck.newsapp.onboarding.domain.repository.LocalUserManager
import jakarta.inject.Inject

class SaveAppEntry @Inject constructor(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke(value : Boolean){
        localUserManager.saveAppEntry(value = value)
    }
}