package com.elflin.examplemovieapps.model

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import java.text.SimpleDateFormat
import java.util.Date

data class Movie(
    val id: Int = -1,
    val overview: String = "",
    @DrawableRes val poster_path: Int = 0,
    val release_date: Date = Date(),
    val title: String = "",
    val vote_average: Float = 0f,
    val vote_count: Int = 0,
    var isLiked: Boolean = false
){
    @SuppressLint("SimpleDateFormat")
    fun getYear():String{
        val df = SimpleDateFormat("yyyy")
        return df.format(release_date)
    }
}