package com.cosmicstruck.newsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import com.cosmicstruck.newsapp.navigation.NavigationSetup
import com.cosmicstruck.newsapp.navigation.Screens
import com.cosmicstruck.newsapp.ui.theme.NewsAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity(): ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()
        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        enableEdgeToEdge()
        setContent {
            NewsAppTheme {
                val navController = rememberNavController()
                Box(
                    modifier =
                    Modifier.background(MaterialTheme.colorScheme.background)
                ) {
                    Log.d("FIRST",viewModel.screen.value.toString())
                    if(viewModel.screen.value == false){
                        NavigationSetup(
                            navController = navController,
                            startDestination = Screens.OnBoardingScreen.route
                        )
                    }

                    else{
                        NavigationSetup(
                            navController = navController,
                            startDestination = Screens.BrowseNewsScreen.route
                        )
                    }
                }
            }
        }
    }
}
