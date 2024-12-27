package com.cosmicstruck.newsapp.browseNews.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cosmicstruck.newsapp.browseNews.data.remote.dto.news.Data
import com.cosmicstruck.newsapp.browseNews.data.remote.service.BrowseNewsService

class NewsPagingSource(
    private val newsService: BrowseNewsService,
): PagingSource<Int, Data>() {
    override fun getRefreshKey(state: PagingState<Int, Data>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Data> {
        val currentOffset = params.key ?: 0
        return try {
            val response = newsService.getNews(
                offset = currentOffset
            )
            val endOfPaginationReached = response.data.isEmpty()
            Log.d("OFFSET CHECKING",currentOffset.toString())
            if(response.data.isNotEmpty()){
                LoadResult.Page(
                    data = response.data,
                    prevKey = if(currentOffset == 0) null else currentOffset - 25,
                    nextKey = if (endOfPaginationReached) null else currentOffset + 25
                )
            }
            else{
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }
}