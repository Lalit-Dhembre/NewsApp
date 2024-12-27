package com.cosmicstruck.newsapp.searchNews.presentation

sealed class SearchEvents {
    data class UpdateSearchQuery(val searchQuery: String) : SearchEvents()

    object SearchNews : SearchEvents()
}