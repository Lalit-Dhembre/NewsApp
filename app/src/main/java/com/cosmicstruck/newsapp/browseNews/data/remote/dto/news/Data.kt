package com.cosmicstruck.newsapp.browseNews.data.remote.dto.news

import com.cosmicstruck.newsapp.bookmark.data.local.entity.BookmarkArticles

data class Data(
    val author: Any,
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


fun Data.toBookmarkArticles(): BookmarkArticles{
    return BookmarkArticles(
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