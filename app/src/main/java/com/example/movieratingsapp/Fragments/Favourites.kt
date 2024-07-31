package com.example.movieratingsapp.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movieratingsapp.MainActivity
import com.example.movieratingsapp.NewsAdapter
import com.example.movieratingsapp.NewsVM
import com.example.movieratingsapp.R
import com.example.movieratingsapp.databinding.FragmentFavouritesBinding
import com.google.android.material.snackbar.Snackbar


class Favourites : Fragment(R.layout.fragment_favourites) {
    lateinit var newsVM: NewsVM
    lateinit var newsAdapter: NewsAdapter
    lateinit var binding: FragmentFavouritesBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFavouritesBinding.bind(view)
        newsVM = (activity as MainActivity).newsVM
        setupFavRecycler()

        newsAdapter.setOnClickListener {
            val bundle = Bundle().apply {
                putSerializable("article",it)
            }
            findNavController().navigate(R.id.action_favouritesFragment_to_articleFragment)
        }
        val itemTouchHelperCallBack = object: ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP or ItemTouchHelper.DOWN,ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                newsVM.deleteArticle(article)
                Snackbar.make(view,"REMOVED FROM FAVOURITES", Snackbar.LENGTH_SHORT).apply {
                    setAction("UNDO"){
                        newsVM.addToFavourites(article)
                    }
                    show()
                }
            }
        }
        ItemTouchHelper(itemTouchHelperCallBack).apply {
            attachToRecyclerView(binding.recyclerFavourites)
        }
        newsVM.getfavNews().observe(viewLifecycleOwner, Observer { articles ->
            newsAdapter.differ.submitList(articles)
        })
    }
    private fun setupFavRecycler(){
        newsAdapter = NewsAdapter()
        binding.recyclerFavourites.apply {
            adapter = newsAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

}