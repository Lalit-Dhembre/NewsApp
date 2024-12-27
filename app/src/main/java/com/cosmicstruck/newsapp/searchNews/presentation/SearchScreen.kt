package com.cosmicstruck.newsapp.searchNews.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.cosmicstruck.newsapp.browseNews.data.remote.dto.news.Data
import com.cosmicstruck.newsapp.browseNews.presentation.components.SearchBar
import com.cosmicstruck.newsapp.homescreen.bottombar.BottomAppBarHome
import com.cosmicstruck.newsapp.utils.ArticleList

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SearchScreen(
    navController: NavController,
    onClick : (Data) -> Unit,
    viewModel: SearchedViewModel = hiltViewModel<SearchedViewModel>()
) {
    val state = viewModel.state.value
    Scaffold(
        bottomBar = { BottomAppBarHome(
            navController = navController
        ) }
    ) {
        Column(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .statusBarsPadding()
        ) {
            SearchBar(
                text = state.searchQuery,
                readOnly = false,
                onValueChange = {
                    Log.d("SEARCH CHECKING","${it} CHANGED")
                    viewModel.onEvent(SearchEvents.UpdateSearchQuery(it)) },
                onSearch = {
                    viewModel.onEvent(SearchEvents.SearchNews)
                },
            )
            Spacer(modifier = Modifier.height(16.dp))
            state.articles?.let {
                val articles = it.collectAsLazyPagingItems()
                ArticleList(
                    articles = articles,
                    onClick = onClick)
            }
        }
    }

}