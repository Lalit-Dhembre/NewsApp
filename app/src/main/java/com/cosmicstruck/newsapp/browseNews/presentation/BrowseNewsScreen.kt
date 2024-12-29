package com.cosmicstruck.newsapp.browseNews.presentation

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.paging.compose.LazyPagingItems
import com.cosmicstruck.newsapp.browseNews.data.remote.dto.news.Data
import com.cosmicstruck.newsapp.R
import com.cosmicstruck.newsapp.bookmark.domain.usecases.NewsUseCases
import com.cosmicstruck.newsapp.browseNews.presentation.components.SearchBar
import com.cosmicstruck.newsapp.homescreen.bottombar.BottomAppBarHome
import com.cosmicstruck.newsapp.homescreen.bottombar.bottomAppBarItems
import com.cosmicstruck.newsapp.navigation.Screens
import com.cosmicstruck.newsapp.newsDetail.NewsDetailWebView
import com.cosmicstruck.newsapp.utils.ArticleList

@Composable
fun BrowseNewsScreen(
    articles : LazyPagingItems<Data>,
    navController: NavController,
    onClick : (Data) -> Unit
){
    val viewModel = hiltViewModel<BrowseNewsViewModel>()
    val backStackEntry = navController.currentBackStackEntryAsState().value
    var selectedItem = rememberSaveable {
        mutableStateOf(0)
    }

    selectedItem.value = when (backStackEntry?.destination?.route) {
        Screens.BrowseNewsScreen.route -> 0
        Screens.SearchScreen.route -> 1
        Screens.BookmarkScreen.route -> 2
        else -> 0
    }
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding(),
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
        },
        bottomBar = { BottomAppBarHome(
            navController = navController
        ) }
    ) {it->
        val titles = remember {
            derivedStateOf {
                if(articles.itemCount > 10){
                    articles.itemSnapshotList.items.slice(IntRange(start = 0, endInclusive = 9)).joinToString(separator = " \uD932\uDFE5"){
                        it.title
                    }
                }else{
                    ""
                }
            }
        }
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp)
            .padding(it)
            .statusBarsPadding()){

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = titles.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
                    .basicMarquee(),
                fontSize = 12.sp,
                color = if(isSystemInDarkTheme()) Color.White else Color.Black
            )
            Spacer(modifier = Modifier.height(8.dp))
            ArticleList(
                articles = articles,
                onClick = onClick,
                onSwipe = {articles->
                    viewModel.addBookmark(articles)
                }
                )
        }

    }


}