package com.cosmicstruck.newsapp.bookmark

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cosmicstruck.newsapp.bookmark.data.local.entity.toData
import com.cosmicstruck.newsapp.bookmark.domain.usecases.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {

    private val _state = mutableStateOf(BookmarkState())
    val state: State<BookmarkState> = _state

    init {
            getArticles()
    }

    fun getArticles() {
        newsUseCases.getArticles.invoke()
            .onEach {news->
                _state.value = _state.value.copy(
                    news
                )
            }.launchIn(viewModelScope)
    }
}