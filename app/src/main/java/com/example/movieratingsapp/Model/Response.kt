package com.example.movieratingsapp.Model

data class Response(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)