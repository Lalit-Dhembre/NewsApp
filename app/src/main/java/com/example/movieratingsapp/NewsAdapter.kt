package com.example.movieratingsapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieratingsapp.Model.Article

class NewsAdapter:RecyclerView.Adapter<NewsAdapter.NewsVH>() {

    inner class NewsVH(itemView:View):RecyclerView.ViewHolder(itemView){
        var articleImage: ImageView = itemView.findViewById(R.id.articleImage)
        var articleSource: TextView = itemView.findViewById(R.id.articleSource)
        var articleTitle: TextView = itemView.findViewById(R.id.articleTitle)
        var articleDescription: TextView = itemView.findViewById(R.id.articleDescription)
        var articleDateTime: TextView = itemView.findViewById(R.id.articleDateTime)

    }
    private val differCallback = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            TODO("Not yet implemented")
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            TODO("Not yet implemented")
            return oldItem == newItem
        }

    }
    val differ = AsyncListDiffer(this,differCallback)


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsVH {
        TODO("Not yet implemented")
        return NewsVH(
            LayoutInflater.from(parent.context).inflate(R.layout.item_news,parent,false)
        )
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
        return differ.currentList.size
    }
    private var onClick: ((Article) -> Unit)? = null

    override fun onBindViewHolder(holder: NewsVH, position: Int) {
        val article = differ.currentList.get(position)
        holder.itemView.apply {
            Glide.with(this).load(article.urlToImage).into(holder.articleImage)
            holder.articleSource.text = article.source?.name
            holder.articleTitle.text = article.title
            holder.articleDateTime.text = article.publishedAt
            holder.articleDescription.text = article.description
            setOnClickListener {
                    onClick?.let {
                        it(article)
                    }
            }
        }
    }
        fun setOnClickListener(listener: (Article) -> Unit){
            onClick = listener
        }
}