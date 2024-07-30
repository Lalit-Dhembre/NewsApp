package com.example.movieratingsapp.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import com.example.movieratingsapp.MainActivity
import com.example.movieratingsapp.NewsVM
import com.example.movieratingsapp.R
import com.example.movieratingsapp.databinding.FragmentArticleBinding
import com.google.android.material.snackbar.Snackbar


class Article : Fragment() {
    lateinit var newsVM: NewsVM
    val args: ArticleArgs by navArgs()
    lateinit var binding: FragmentArticleBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsVM = (activity as MainActivity).newsVM
        val article = args.article1

        binding.webView.apply {
            webViewClient = WebViewClient()
            article.url?.let {
                loadUrl(it)
            }
        }
        binding.fab.setOnClickListener{
            newsVM.addToFavourites(article)
            this.view?.let { it1 -> Snackbar.make(it1,"Added to Favourites",Snackbar.LENGTH_SHORT).show() }
        }
    }
}