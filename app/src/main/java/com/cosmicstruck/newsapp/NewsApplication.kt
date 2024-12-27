package com.cosmicstruck.newsapp

import android.app.Application
import com.cosmicstruck.newsapp.onboarding.domain.usecases.OnBoardingUseCases
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class NewsApplication: Application() {
}