package com.cosmicstruck.newsapp

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cosmicstruck.newsapp.onboarding.domain.usecases.OnBoardingUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCases: OnBoardingUseCases
): ViewModel() {

    private val _screen = mutableStateOf(false)
    val screen: State<Boolean> = _screen
    init {
        viewModelScope.launch{
            getAppEntry()
        }
    }
    suspend fun getAppEntry(){
         useCases.getAppEntry.invoke().collect{
            _screen.value = it
        }
    }
}