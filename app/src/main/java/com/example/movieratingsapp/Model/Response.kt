package com.example.movieratingsapp.Model

data class Response1(
    val articles: MutableList<Article>,
    val status: String,
    val totalResults: Int
)