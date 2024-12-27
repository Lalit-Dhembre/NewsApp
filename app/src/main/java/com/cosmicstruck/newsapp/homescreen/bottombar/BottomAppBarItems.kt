package com.cosmicstruck.newsapp.homescreen.bottombar

import androidx.annotation.DrawableRes
import androidx.compose.ui.res.painterResource
import com.cosmicstruck.newsapp.navigation.Screens
import com.cosmicstruck.newsapp.R

data class BottomAppBarItems(
     val title: String,
     val route : String,
    @DrawableRes val image : Int
)
val bottomAppBarItems = listOf(
    BottomAppBarItems(
        title = "Browse News",
        route = Screens.BrowseNewsScreen.route,
        image = R.drawable.ic_home
    ),
    BottomAppBarItems(
        title = "Search News",
        route = Screens.SearchScreen.route,
        image = R.drawable.ic_search
    ),
    BottomAppBarItems(
        title = "Bookmarks",
        route = Screens.BookmarkScreen.route,
        image = R.drawable.ic_bookmark
    )
)

