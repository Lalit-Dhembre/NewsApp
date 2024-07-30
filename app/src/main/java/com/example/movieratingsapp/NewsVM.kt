package com.example.movieratingsapp

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.movieratingsapp.Model.Article
import com.example.movieratingsapp.Model.Response1
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.io.IOException

class NewsVM(app: Application, val newsRepo: Repo) : AndroidViewModel(app) {
    val headlines: MutableLiveData<Resource<Response1>> = MutableLiveData()
    var headlinesPage = 1
    var headlinesResponse: Response1? = null
    val search: MutableLiveData<Resource<Response1>> = MutableLiveData()
    var searchNewsPage = 1
    var searchResponse: Response1? = null
    var newSearchQuery: String? = null
    var oldSearchQuery: String? = null

    init {
        getHeadlines("ind")
    }
    fun getHeadlines(countryCode: String) = viewModelScope.launch {
        headlines(countryCode)
    }
    fun searchNews(searchQuery: String) = viewModelScope.launch {
        searchNewsInternet(searchQuery)
    }

    private fun handleHeadlinesResponse(response: retrofit2.Response<Response1>): Resource<Response1> {
        if (response.isSuccessful) {
            response.body()?.let { resultres ->
                headlinesPage++
                if (headlinesResponse == null) {
                    headlinesResponse = resultres
                } else {
                    val oldArticle = headlinesResponse?.articles
                    val newsArticle = resultres.articles
                    oldArticle?.addAll(newsArticle)
                }
                return Resource.Success(headlinesResponse ?: resultres)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: retrofit2.Response<Response1>): Resource<Response1> {
        if (response.isSuccessful) {
            response.body()?.let { resultres ->
                if (searchResponse == null || newSearchQuery != oldSearchQuery) {
                    searchNewsPage = 1
                    oldSearchQuery = newSearchQuery
                    searchResponse = resultres
                } else {
                    searchNewsPage++
                    val oldArticles = searchResponse?.articles
                    val newArticle = resultres.articles
                    oldArticles?.addAll(newArticle)
                }
                return Resource.Success(headlinesResponse ?: resultres)
            }
        }
        return Resource.Error(response.message())
    }

    fun addToFavourites(article: Article) = viewModelScope.launch {
        newsRepo.upsert(article)
    }

    fun getfavNews() = newsRepo.getfav()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepo.delete(article)
    }

    fun internetConnection(context: Context): Boolean {
        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
            return getNetworkCapabilities(activeNetwork)?.run {
                when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                    else -> false
                }
            } ?: false
        }
    }

    private suspend fun headlines(countryCode: String) {
        headlines.postValue(Resource.Loading())
        try {
            if (internetConnection(this.getApplication())) {
                val response1 = newsRepo.getHeadlines(countryCode, headlinesPage)
                headlines.postValue(handleHeadlinesResponse(response1))
            } else {
                headlines.postValue(Resource.Error("NO INTERNET"))
            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> headlines.postValue(Resource.Error("UNABLE TO CONNECT"))
                else -> headlines.postValue(Resource.Error("No signal"))
            }
        }
    }
    private suspend fun searchNewsInternet(searchQuery: String){
        newSearchQuery = searchQuery
        search.postValue(Resource.Loading())
        try{
            if(internetConnection(this.getApplication())){
                val response = newsRepo.searchNews(searchQuery,searchNewsPage)
                search.postValue(handleSearchNewsResponse(response))
            }
            else{
                search.postValue(Resource.Error("No internet"))
            }
        } catch (t:Throwable){
            when(t){
                is IOException -> search.postValue(Resource.Error("Unable to Connect"))
                else-> search.postValue(Resource.Error("No internet"))
            }
        }
    }

    fun fetchHeadlines(countryCode: String) = viewModelScope.launch {
        headlines(countryCode)
    }
}


class NewsVMPF(val app: Application, val newsRepo: Repo): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return super.create(modelClass) as T
    }
}