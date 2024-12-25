package com.cosmicstruck.newsapp.onboarding.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cosmicstruck.newsapp.onboarding.domain.OnBoardingPages
import com.cosmicstruck.newsapp.onboarding.domain.pages

@Composable
fun OnBoardingItem(
    page: OnBoardingPages
){
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(page.image),
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f),
            contentScale = ContentScale.FillBounds
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = page.title,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            color = if(isSystemInDarkTheme()) Color.White else Color.Black,
            modifier = Modifier
                .padding(5.dp)
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = page.description,
            fontSize = 15.sp,
            color = if (isSystemInDarkTheme()) Color.White.copy(alpha = 0.6f) else Color.Black.copy(alpha = 0.6f),
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(5.dp)
        )
    }
}

@Preview
@Composable
fun OnBoardingPagePreview(){
    OnBoardingItem(page = pages[0])
}