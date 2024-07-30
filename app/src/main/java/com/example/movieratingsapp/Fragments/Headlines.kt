package com.example.movieratingsapp.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
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


class Headlines : Fragment() {
    lateinit var newsVM: NewsVM
    lateinit var adapter: NewsAdapter
    lateinit var retry:Button
    lateinit var error:TextView
    lateinit var newsAdapter:NewsAdapter
    lateinit var itemHeadings:CardView
    lateinit var binding: FragmentHeadlinesBinding


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHeadlinesBinding.bind(view)
        itemHeadings = view.findViewById(R.id.itemHeadlinesError)
        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view:View = inflater.inflate(R.layout.item_error,null)
        retry = view.findViewById(R.id.retryButton)
        error = view.findViewById(R.id.errorText)

        newsVM = (activity as MainActivity).newsVM
        setupHeadlineRecycler()

        newsAdapter.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_headlinesFragment_to_articleFragment)
        }
        newsVM.headlines.observe(viewLifecycleOwner, Observer {
            response ->
            when(response){
                is Resource.Success<*> ->{
                    hideProgress()
                    hideError()
                    response.data?.let {
                        Response1 -> newsAdapter.differ.submitList(Response1.articles.toList())
                        val totalPages = Response1.totalResults / Constants.QUERY_PAGE_SIZE
                        isLastPage = newsVM.headlinesPage == totalPages
                        if(isLastPage){
                            binding.recyclerHeadlines.setPadding(0,0,0,0)
                        }
                    }
                }
                is Resource.Error<*> ->{
                    hideProgress()
                    response.message?.let {message->
                        Toast.makeText(activity, "Sorry Error: ${message}", Toast.LENGTH_SHORT).show()
                    }
                }
                is Resource.Loading<*> ->{
                    showProgress()
                }
            }
        })
        retry.setOnClickListener {
            newsVM.getHeadlines("ind")
        }
    }

    var isError = false
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private fun hideProgress(){
        binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgress(){
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private fun hideError(){
        itemHeadings.visibility = View.INVISIBLE
        isError = false
    }

    private fun showError(){
        itemHeadings.visibility = View.VISIBLE
        isError = true
    }

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisible = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCnt = layoutManager.childCount
            val totalItemCnt = layoutManager.itemCount
            val isNoError = !isError
            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtbegin = firstVisible >= 0
            val isAtLast = firstVisible + visibleItemCnt >= totalItemCnt
            val isTotalMore = totalItemCnt >= Constants.QUERY_PAGE_SIZE
            val shouldPaginate = isNoError && isNotLoadingAndNotLastPage && isAtLast
            if (shouldPaginate) {
                newsVM.getHeadlines("ind")
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
        private fun setupHeadlineRecycler(){
            var newsAdapter = NewsAdapter()
            binding.recyclerHeadlines.apply {
                adapter = newsAdapter
                layoutManager = LinearLayoutManager(activity)
                addOnScrollListener(this@Headlines.scrollListener)
            }
        }

}