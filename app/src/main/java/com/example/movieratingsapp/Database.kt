package com.example.movieratingsapp

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.movieratingsapp.Model.Article

abstract class Database: RoomDatabase() {
    abstract fun getArticlesDao(): Dao

    companion object {
        @Volatile
        private var instance: Database? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: createDatabase(context).also {
                instance = it
            }
        }
        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                Database::class.java,
                "articlesdb"
            ).build()


    }
}