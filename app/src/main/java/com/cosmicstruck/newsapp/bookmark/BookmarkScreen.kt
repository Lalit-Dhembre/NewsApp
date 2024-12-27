package com.cosmicstruck.newsapp.bookmark

import android.R.attr.onClick
import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.cosmicstruck.newsapp.bookmark.data.local.entity.toData
import com.cosmicstruck.newsapp.browseNews.data.remote.dto.news.Data
import com.cosmicstruck.newsapp.browseNews.presentation.components.NewsCard
import com.cosmicstruck.newsapp.homescreen.bottombar.BottomAppBarHome
import com.cosmicstruck.newsapp.utils.ArticleList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.onEach

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun BookmarkScreen(
    navigateToDetails: (Data) -> Unit,
    navController: NavController
) {
    val viewModel = hiltViewModel<BookmarkViewModel>()
    val state = viewModel.state.value

    Log.d("CHECKING STATE OF BOOK MARKS",state.articles.toString())
    Scaffold(
        bottomBar = { BottomAppBarHome(navController = navController) }
    ) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(top = 8.dp, start = 8.dp, end = 8.dp)
    ) {

        Text(
            text = "Bookmark",
            style = MaterialTheme.typography.displayMedium.copy(fontWeight = FontWeight.Bold),
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(all = 4.dp)
        ) {
            if(state.articles != null) {
                items(items = state.articles) { it ->
                    NewsCard(
                        data = it.toData(),
                        onClick = { navigateToDetails(it.toData()) },
                    )
                }
            }
            else{

            }
        }
    }
}
}