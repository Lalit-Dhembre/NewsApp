package com.cosmicstruck.newsapp.browseNews.data.remote.dto.news

data class Pagination(
    val count: Int,
    val limit: Int,
    val offset: Int,
    val total: Int
)