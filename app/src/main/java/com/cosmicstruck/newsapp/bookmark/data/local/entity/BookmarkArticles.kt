package com.cosmicstruck.newsapp.bookmark.data.local.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cosmicstruck.newsapp.browseNews.data.remote.dto.news.Data

@Entity
data class BookmarkArticles(
    @PrimaryKey(autoGenerate = true)
    val id : Int = 0,
    val author : String ="",
    val category: String,
    val country: String,
    val description: String,
    val image: String?,
    val language: String,
    val published_at: String,
    val source: String,
    val title: String,
    val url: String
)

fun BookmarkArticles.toData(): Data{
    return Data(
        author = "",
        category = category,
        country = country,
        description = description,
        image = image,
        language = language,
        published_at = published_at,
        source = source,
        title = title,
        url = url
    )
}