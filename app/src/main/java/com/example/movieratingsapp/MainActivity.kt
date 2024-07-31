package com.example.movieratingsapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.movieratingsapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var newsVM: NewsVM
    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Use AppDatabase to get the database instance
        val newsRepo = Repo(AppDatabase.getInstance(this))
        val viewModelProvider = NewsVMPF(application, newsRepo)
        newsVM = ViewModelProvider(this, viewModelProvider).get(NewsVM::class.java)

        val navHost = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment) as NavHostFragment
        val navController = navHost.navController
        binding.bottomNavigationView.setupWithNavController(navController)
    }
}
