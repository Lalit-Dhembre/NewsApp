package com.cosmicstruck.newsapp.searchNews.presentation

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.cosmicstruck.newsapp.R
import com.cosmicstruck.newsapp.browseNews.data.remote.dto.news.Data
import com.cosmicstruck.newsapp.browseNews.presentation.components.SearchBar
import com.cosmicstruck.newsapp.homescreen.bottombar.BottomAppBarHome
import com.cosmicstruck.newsapp.utils.ArticleList

@Composable
fun SearchScreen(
    navController: NavController,
    onClick : (Data) -> Unit,
    viewModel: SearchedViewModel = hiltViewModel<SearchedViewModel>()
) {
    val state = viewModel.state.value
    Scaffold(
        modifier = Modifier
            .statusBarsPadding(),
        bottomBar = { BottomAppBarHome(
            navController = navController
        ) },
        topBar = {
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(
                    color = Color(0xFFE52323)
                )){
                Row(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically

                ) {
                    Image(
                        painter = painterResource(R.drawable.anchor_logo),
                        contentDescription = null
                    )
                    Text(
                        text = "Anchor News",
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    ) {it->
        Column(
            modifier = Modifier
                .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                .statusBarsPadding()
                .padding(it)
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