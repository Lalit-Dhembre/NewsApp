package com.cosmicstruck.newsapp.searchNews.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.cosmicstruck.newsapp.searchNews.domain.usecaase.SearchNewsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchedViewModel @Inject constructor(
   private val useCase: SearchNewsUseCase
): ViewModel() {

    private var _state = mutableStateOf(SearchScreenState())
    val state: State<SearchScreenState> = _state

    fun onEvent(event : SearchEvents){
        when(event){
            SearchEvents.SearchNews -> {
                searchNews()
            }
            is SearchEvents.UpdateSearchQuery -> {
                _state.value = _state.value.copy(
                    searchQuery = event.searchQuery
                )
            }
        }
    }

    private fun searchNews(){
        val news = useCase.invoke(
            keyword = _state.value.searchQuery
        ).cachedIn(viewModelScope)
        _state.value = _state.value.copy(
            articles = news
        )
    }
}