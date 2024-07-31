package com.example.movieratingsapp.Fragments

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.movieratingsapp.NewsAdapter
import com.example.movieratingsapp.NewsVM
import com.example.movieratingsapp.R
import com.example.movieratingsapp.Resource
import com.example.movieratingsapp.databinding.FragmentSearchBinding
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class Search : Fragment(R.layout.fragment_search) {
    lateinit var newsVM: NewsVM
    lateinit var newsAdapter: NewsAdapter
    lateinit var retryButton: Button
    lateinit var errorText: TextView
    lateinit var itemSearchError: CardView
    lateinit var binding: FragmentSearchBinding



   override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSearchBinding.bind(view)
        itemSearchError = view.findViewById(R.id.itemSearchError)
        val inflater = requireContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val errorView: View = inflater.inflate(R.layout.item_error, null)
        retryButton = errorView.findViewById(R.id.retryButton)
        errorText = errorView.findViewById(R.id.errorText)

        newsVM = (activity as MainActivity).newsVM
        setupSearchRecycler()

        newsAdapter.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("article", it)
            }
            findNavController().navigate(R.id.action_searchFragment_to_articleFragment, bundle)
        }

        var job: Job? = null
        binding.searchEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                TODO("Not yet implemented")
            }

            override fun afterTextChanged(editable: Editable?) {
                job?.cancel()
                job = MainScope().launch {
                    delay(Constants.SEARCH_NEWS_TIME)
                    editable?.let {
                        if (editable.toString().isNotEmpty()) {
                            newsVM.searchNews(editable.toString())
                        }
                    }
                }
            }
        })

       newsVM.search.observe(viewLifecycleOwner, Observer {
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
                           binding.recyclerSearch.setPadding(0,0,0,0)
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
       retryButton.setOnClickListener{
           if(binding.searchEdit.text.toString().isNotEmpty()){
               newsVM.searchNews(binding.searchEdit.text.toString())
           }
           else{
               hideError()
           }
       }
    }

    var isError = false
    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private fun hideProgress() {
        binding.paginationProgressBar.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgress() {
        binding.paginationProgressBar.visibility = View.VISIBLE
        isLoading = true
    }

    private fun hideError() {
        itemSearchError.visibility = View.INVISIBLE
        isError = false
    }

    private fun showError() {
        itemSearchError.visibility = View.VISIBLE
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
                newsVM.searchNews(binding.searchEdit.text.toString())
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

    private fun setupSearchRecycler() {
        var newsAdapter = NewsAdapter()
        binding.recyclerSearch.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@Search.scrollListener)
        }
    }
}