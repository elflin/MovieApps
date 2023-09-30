package com.elflin.movieapps.model

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import java.text.SimpleDateFormat
import java.util.Date

data class Movie(
    val overview: String,
    @DrawableRes val poster_path: Int,
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
