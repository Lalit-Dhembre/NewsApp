package com.cosmicstruck.newsapp.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.paging.compose.collectAsLazyPagingItems
import com.cosmicstruck.newsapp.bookmark.BookmarkScreen
import com.cosmicstruck.newsapp.browseNews.data.remote.dto.news.Data
import com.cosmicstruck.newsapp.browseNews.presentation.BrowseNewsScreen
import com.cosmicstruck.newsapp.browseNews.presentation.BrowseNewsViewModel
import com.cosmicstruck.newsapp.newsDetail.NewsDetailWebView
import com.cosmicstruck.newsapp.onboarding.presentation.OnBoardingScreen
import com.cosmicstruck.newsapp.searchNews.presentation.SearchScreen

@Composable
fun NavigationSetup(
    navController: NavHostController,
    startDestination : String
){
    NavHost(
        navController = navController,
        startDestination = startDestination
    ){
        composable(route = Screens.OnBoardingScreen.route){
            OnBoardingScreen(
                navController = navController
            )
        }
        composable(route = Screens.BrowseNewsScreen.route){
            val viewModel : BrowseNewsViewModel = hiltViewModel()
            BrowseNewsScreen(
                articles = viewModel.news.collectAsLazyPagingItems(),
                onClick = { data ->
                    Log.d("FROM BROWSE NEWS SCREEN NAV GRAPH","${data.title}")
                    navigateToDetails(
                        navController = navController,
                        data = data
                    )
                },
                navController = navController

            )
        }

        composable(route = Screens.SearchScreen.route){
            SearchScreen(
                navController,
                onClick = {it->
                    navigateToDetails(
                        navController,
                        it
                    )
                })
        }
        composable(route = Screens.BookmarkScreen.route){
            BookmarkScreen(
                navigateToDetails = { it ->
                    navigateToDetails(
                        navController,
                        it
                    )
                },
                navController = navController,
            )
        }
        composable(route = Screens.NewsDetailScreen.route){
            Log.d("REACHED","REAHED HERE")
            navController.previousBackStackEntry?.savedStateHandle?.get<String>("url")?.let {it->
                NewsDetailWebView(
                    url = it
                )
            }
        }
    }
}
private fun navigateToDetails(
    navController: NavHostController,
    data: Data
){
    navController.currentBackStackEntry?.savedStateHandle?.set("url",data.url)
    Log.d("FROM NAVIGATION","${data.url}")
    navController.navigate(
        route = Screens.NewsDetailScreen.route
    )
}