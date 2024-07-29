package com.example.movieratingsapp

import androidx.room.TypeConverters
import com.example.movieratingsapp.Model.Source

class Converters {
    @TypeConverters
    fun fromSource(source: Source): String{
        return source.name
    }

    @TypeConverters
    fun toSource(name: String): Source{
        return Source(name,name)
    }
}