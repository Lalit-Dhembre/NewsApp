package com.cosmicstruck.newsapp.onboarding.domain.model

import androidx.annotation.DrawableRes
import com.cosmicstruck.newsapp.R

data class OnBoardingPages(
    val title : String,
    val description: String,
    @DrawableRes val image : Int
)

val pages : List<OnBoardingPages> = listOf(
    OnBoardingPages(
        title = "Stay Updated with the Latest News",
        description = "Explore trending topics and stay informed with news tailored to your interests. Get the most relevant updates in one place.",
        image = R.drawable.browse
    ),
    OnBoardingPages(
        title = "Find What Matters to You",
        description = "Use the powerful search feature to discover news that matches your curiosity. Filter by topic, region, or keyword effortlessly.",
        image = R.drawable.search
    ),
    OnBoardingPages(
        title = "Save and Access Anytime",
        description = "Bookmark articles to create your personal news library. Read your favorite stories anytime, even offline.",
        image = R.drawable.bookmark
    )
)
