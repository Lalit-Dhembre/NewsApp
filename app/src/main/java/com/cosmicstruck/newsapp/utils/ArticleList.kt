package com.cosmicstruck.newsapp.utils

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.cosmicstruck.newsapp.browseNews.data.remote.dto.news.Data
import com.cosmicstruck.newsapp.browseNews.presentation.components.NewsCard

@Composable
fun ArticleList(
    articles : LazyPagingItems<Data>,
    onClick : (Data) -> Unit,
    onSwipe : ((Data) -> Unit) ? = null
){
    val handlePagingResult = handlePagingResult(articles)
    Log.d("ARTICLE LIST REACHED","REACHED IN ARTICLE LIST")
    if(handlePagingResult){
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(all = 4.dp)
        ) {
            Log.d("CHECKING","ARTICLE LIST ${articles.itemCount}")
            items(count = articles.itemCount){
                articles[it]?.let { articles->
                    NewsCard(data = articles,
                        onSwipe = { onSwipe?.invoke(articles) },
                        onClick = {
                            Log.d("CLICKED FROM ARTICLE LIST","${articles.title}")
                            onClick(articles)})
                }
            }
        }
    }
}

@Composable
fun handlePagingResult(
    articles: LazyPagingItems<Data>
): Boolean{
    val loadState = articles.loadState

    val error = when{
        loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
        loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
        loadState.append is LoadState.Error -> loadState.append as LoadState.Error
        else -> null
    }

    return when{
        loadState.refresh is LoadState.Loading ->{
            Log.d("REFRESH","REFRESHING")
            false
        }

        error != null ->{
            Log.d("ERROR","${error.toString()}")
            false
        }

        else -> {
            Log.d("SUCCESS","Succeed")
            true
        }
    }

}