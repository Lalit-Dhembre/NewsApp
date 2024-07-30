package com.example.movieratingsapp

import com.example.movieratingsapp.API.Retrofit_Instance
import com.example.movieratingsapp.Model.Article
import com.example.movieratingsapp.Model.Response

class Repo(val db: Database) {
    suspend fun getHeadlines(countryCode: String, pageno: Int): retrofit2.Response<Response> {
        return Retrofit_Instance.api.getHeadlines(countryCode, pageno)
    }

    suspend fun searchNews(searchQuery: String, pageno: Int): retrofit2.Response<Response> {
        return Retrofit_Instance.api.getSearch(searchQuery, pageno)
    }

    suspend fun upsert(article: Article) = db.getArticlesDao().upsert(article)

    suspend fun delete(article: Article) = db.getArticlesDao().delete(article)

    fun getAll() = db.getArticlesDao().getAll()

    fun getfav() = db.getArticlesDao().getAll()
}
