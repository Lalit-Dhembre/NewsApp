package com.cosmicstruck.newsapp.browseNews.presentation


data class HomeState(
val newsTicker: String = "",
val isLoading: Boolean = false,
)