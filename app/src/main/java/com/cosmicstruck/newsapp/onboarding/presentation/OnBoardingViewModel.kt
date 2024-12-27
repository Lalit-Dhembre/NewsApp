package com.cosmicstruck.newsapp.onboarding.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cosmicstruck.newsapp.onboarding.domain.repository.LocalUserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val localUserManager: LocalUserManager
): ViewModel() {
    fun saveAppEntryTrue(){
       viewModelScope.launch{ localUserManager.saveAppEntry(true)
    }}

    fun saveAppEntryFalse(){
       viewModelScope.launch{ localUserManager.saveAppEntry(false)
    }}

    fun getAppEntry() : Flow<Boolean>{
        return localUserManager.readAppEntry()
    }
}