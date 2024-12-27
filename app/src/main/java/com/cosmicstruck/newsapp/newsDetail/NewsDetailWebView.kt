package com.cosmicstruck.newsapp.newsDetail

import android.graphics.Bitmap
import android.util.Log
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.web.AccompanistWebViewClient
import com.google.accompanist.web.rememberWebViewNavigator
import com.google.accompanist.web.rememberWebViewState

@Composable
fun NewsDetailWebView(
    url : String
    ){
    Log.d("FROM WEBVIEW","REACHED HERE")
    AndroidView(
        factory = {
            WebView(it).apply {
                webViewClient = WebViewClient()
                loadUrl(url)

            }
        },
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
    )

}