package com.example.movieratingsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.movieratingsapp.Model.Article

class NewsAdapter:RecyclerView.Adapter<NewsAdapter.NewsVH>() {

    inner class NewsVH(itemView:View):RecyclerView.ViewHolder(itemView){

    }

    private val differCallback = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            TODO("Not yet implemented")
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            TODO("Not yet implemented")
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsVH {
        TODO("Not yet implemented")
        return NewsVH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        )
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: NewsVH, position: Int) {
        TODO("Not yet implemented")
    }
}