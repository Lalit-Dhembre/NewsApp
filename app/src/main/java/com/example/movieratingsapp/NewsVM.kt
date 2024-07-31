package com.example.movieratingsapp

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieratingsapp.Model.Article
import com.example.movieratingsapp.Model.Response1
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.IOException

class NewsVM(
    app: Application,
    private val newsRepo: Repo
) : AndroidViewModel(app) {

    val headlines: MutableLiveData<Resource<Response1>> = MutableLiveData()
    var headlinesPage = 1
    var headlinesResponse: Response1? = null
    val search: MutableLiveData<Resource<Response1>> = MutableLiveData()
    var searchNewsPage = 1
    var searchResponse: Response1? = null
    var newSearchQuery: String? = null
    var oldSearchQuery: String? = null

    init {
        getHeadlines("us")
    }

    fun getHeadlines(countryCode: String) = viewModelScope.launch {
        headlines.postValue(Resource.Loading())
        try {
            if (internetConnection(getApplication())) {
                val response = newsRepo.getHeadlines(countryCode, headlinesPage)
                headlines.postValue(handleHeadlinesResponse(response))
            } else {
                headlines.postValue(Resource.Error("NO INTERNET"))
            }
        } catch (e: Throwable) {
            when (e) {
                is IOException -> headlines.postValue(Resource.Error("UNABLE TO CONNECT"))
                else -> headlines.postValue(Resource.Error("No signal"))
            }
        }
    }

    fun searchNews(searchQuery: String) = viewModelScope.launch {
        search.postValue(Resource.Loading())
        try {
            if (internetConnection(getApplication())) {
                val response = newsRepo.searchNews(searchQuery, searchNewsPage)
                search.postValue(handleSearchNewsResponse(response))
            } else {
                search.postValue(Resource.Error("No internet"))
            }
        } catch (e: Throwable) {
            when (e) {
                is IOException -> search.postValue(Resource.Error("Unable to Connect"))
                else -> search.postValue(Resource.Error("No internet"))
            }
        }
    }

    private fun handleHeadlinesResponse(response: retrofit2.Response<Response1>): Resource<Response1> {
        Log.d("NewsVM", "Response Code: ${response.code()}")
        Log.d("NewsVM", "Response Body: ${response.body()}")
        return if (response.isSuccessful) {
            response.body()?.let { result ->
                headlinesPage++
                headlinesResponse = headlinesResponse?.apply {
                    articles.addAll(result.articles)
                } ?: result
                Resource.Success(headlinesResponse ?: result)
            } ?: Resource.Error("Empty response body")
        } else {
            Resource.Error(response.message())
        }
    }

    private fun handleSearchNewsResponse(response: retrofit2.Response<Response1>): Resource<Response1> {
        return if (response.isSuccessful) {
            response.body()?.let { result ->
                if (searchResponse == null || newSearchQuery != oldSearchQuery) {
                    searchNewsPage = 1
                    oldSearchQuery = newSearchQuery
                    searchResponse = result
                } else {
                    searchNewsPage++
                    searchResponse?.articles?.addAll(result.articles)
                }
                Resource.Success(searchResponse ?: result)
            } ?: Resource.Error("Empty response body")
        } else {
            Resource.Error(response.message())
        }
    }

    fun addToFavourites(article: Article) = viewModelScope.launch {
        newsRepo.upsert(article)
    }

    fun getfavNews() = newsRepo.getfav()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepo.delete(article)
    }

    private fun internetConnection(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return networkCapabilities?.run {
            hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } ?: false
    }
}


class NewsVMPF(
    private val app: Application,
    private val newsRepo: Repo
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsVM::class.java)) {
            return NewsVM(app, newsRepo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}