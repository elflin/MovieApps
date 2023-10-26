package com.elflin.movieapps.model

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date

data class Movie(
    val id: Int = -1,
    val overview: String,
    val poster_path: String,
    val release_date: Date,
    val title: String,
    val vote_average: Float,
    val vote_count: Int,
    var isLiked: Boolean = false
){

    @SuppressLint("SimpleDateFormat")
    fun getYear(): String{
        val df = SimpleDateFormat("yyyy")
        return df.format(release_date)
    }

}
