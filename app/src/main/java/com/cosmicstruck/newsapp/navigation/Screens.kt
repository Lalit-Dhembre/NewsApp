package com.cosmicstruck.newsapp.navigation

sealed class Screens(val route : String) {

    object BrowseNewsScreen: Screens(route = "browse_screen")

    object OnBoardingScreen : Screens(route = "onboarding_screen")


    object SearchScreen : Screens(route = "search_screen")

    object BookmarkScreen : Screens(route = "bookmark_screen")

    object NewsDetailScreen : Screens(route = "news_detail")
}