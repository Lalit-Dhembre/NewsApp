package com.example.movieratingsapp.Fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieratingsapp.Constants
import com.example.movieratingsapp.MainActivity
import com.example.movieratingsapp.Model.Response1
import com.example.movieratingsapp.NewsAdapter
import com.example.movieratingsapp.NewsVM
import com.example.movieratingsapp.R
import com.example.movieratingsapp.Resource
import com.example.movieratingsapp.databinding.FragmentHeadlinesBinding

class Headlines : Fragment(R.layout.fragment_headlines) {
    private lateinit var newsVM: NewsVM
    private lateinit var newsAdapter: NewsAdapter
    private lateinit var retry: Button
    private lateinit var error: TextView
    private lateinit var itemHeadings: CardView
    private lateinit var binding: FragmentHeadlinesBinding

    private var isError = false
    private var isLoading = false
    private var isLastPage = false
    private var isScrolling = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHeadlinesBinding.bind(view)
        itemHeadings = view.findViewById(R.id.itemHeadlinesError)
        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val errorView: View = inflater.inflate(R.layout.item_error, null)
        retry = errorView.findViewById(R.id.retryButton)
        error = errorView.findViewById(R.id.errorText)

        newsVM = (activity as MainActivity).newsVM
        setupHeadlineRecycler()

        newsAdapter.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_headlinesFragment_to_articleFragment, bundle)
        }

        newsVM.headlines.observe(viewLifecycleOwner, Observer { response ->
            when (response) {
                is Resource.Success<*> -> {
                    hideProgress()
                    hideError()
                    response.data?.let {
                            Response1 -> newsAdapter.differ.submitList(Response1.articles.toList())
                        val totalPages = Response1.totalResults / Constants.QUERY_PAGE_SIZE
                        isLastPage = newsVM.headlinesPage == totalPages
                        if (isLastPage) {
                            binding.recyclerHeadlines.setPadding(0, 0, 0, 0)
                        }
                    }
                }
                is Resource.Error<*> -> {
                    hideProgress()
                    response.message?.let { message ->
                        Toast.makeText(activity, "Sorry Error: $message", Toast.LENGTH_SHORT).show()
                    }
                    showError()
                }
                is Resource.Loading<*> -> {
                    showProgress()
                }
            }
        })

        retry.setOnClickListener {
            newsVM.getHeadlines("us")
        }
    }

    private fun hideProgress() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgress() {
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private fun hideError() {
        itemHeadings.visibility = View.INVISIBLE
        isError = false
    }

    private fun showError() {
        itemHeadings.visibility = View.VISIBLE
        isError = true
    }

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisible = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCnt = layoutManager.childCount
            val totalItemCnt = layoutManager.itemCount
            val isNoError = !isError
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtBegin = firstVisible >= 0
            val isAtLast = firstVisible + visibleItemCnt >= totalItemCnt
            val isTotalMore = totalItemCnt >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate = isNoError && isNotLoadingAndNotLastPage && isAtLast
            if (shouldPaginate) {
                newsVM.getHeadlines("us")
                isScrolling = false
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }

    private fun setupHeadlineRecycler() {
        newsAdapter = NewsAdapter()
        binding.recyclerHeadlines.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(scrollListener)
        }
    }
}
