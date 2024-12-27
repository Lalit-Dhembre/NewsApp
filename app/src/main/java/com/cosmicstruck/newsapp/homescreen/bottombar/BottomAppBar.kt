package com.cosmicstruck.newsapp.homescreen.bottombar

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.cosmicstruck.newsapp.R
import com.cosmicstruck.newsapp.navigation.Screens

@Composable
fun BottomAppBarHome(
    navController: NavController
){
    val items = listOf(
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

    NavigationBar(
        modifier = Modifier.fillMaxWidth(),
        containerColor = MaterialTheme.colorScheme.background,
        tonalElevation = 10.dp
    ) {
        val navBackStackEntry = navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry.value?.destination?.route
        items.forEachIndexed { index, item->
            NavigationBarItem(
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(route = item.route){
                        popUpTo(
                            navController.graph.startDestinationId
                        )
                        launchSingleTop = true
                    }
                },
                icon = {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(item.image),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp),
                            tint = if(!isSystemInDarkTheme()) Color.Black.copy(alpha = 0.7f) else Color.White.copy(alpha = 0.7f)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                },
                colors = NavigationBarItemColors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    selectedIndicatorColor = MaterialTheme.colorScheme.primaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.inversePrimary,
                    unselectedTextColor = MaterialTheme.colorScheme.inversePrimary,
                    disabledIconColor = MaterialTheme.colorScheme.inversePrimary,
                    disabledTextColor = MaterialTheme.colorScheme.inversePrimary
                )
            )
        }
    }
}