package com.example.movieratingsapp

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.movieratingsapp.Model.Article
import com.example.movieratingsapp.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    lateinit var newsVM: NewsVM
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val newsRepo = Repo(Database(this))
        val viewModelProvider = NewsVMPF(application,newsRepo)
        newsVM = ViewModelProvider(this,viewModelProvider).get(NewsVM::class.java)
        val navHost = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHost.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}