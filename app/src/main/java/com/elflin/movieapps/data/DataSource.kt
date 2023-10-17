package com.elflin.movieapps.data

import android.annotation.SuppressLint
import com.elflin.movieapps.R
import com.elflin.movieapps.model.Movie
import java.text.SimpleDateFormat

class DataSource {

    @SuppressLint("SimpleDateFormat")
    public fun loadMovie(): List<Movie>{
        return listOf<Movie>(
            Movie(1,"An American family moves in to the Canterville Chase, a London mansion that has been haunted by ghost Sir Simon De Canterville for 300 years.", R.drawable.image_1, SimpleDateFormat("yyyy-MM-dd").parse("2023-09-22")!!, "The Canterville Ghost", 0f, 0),
            Movie(2,"A concert film documenting Talking Heads at the height of their popularity, on tour for their 1983 album \\\"Speaking in Tongues.\\\" The band takes the stage one by one and is joined by a cadre of guest musicians for a career-spanning and cinematic performance that features creative choreography and visuals.", R.drawable.image_2, SimpleDateFormat("yyyy-MM-dd").parse("1984-11-16")!!, "Stop Making Sense", 8.2f, 208),
            Movie(3,"A novelist attends the trial of a woman accused of killing her 15-month-old daughter by abandoning her to the rising tide on a beach in northern France. But as the trial continues, her own family history, doubts, and fears about motherhood are steadily dislodged as the life story of the accused is gradually revealed.", R.drawable.image_3, SimpleDateFormat("yyyy-MM-dd").parse("2022-11-23")!!, "Saint Omer", 6.3f, 99),
            Movie(4,"When Maya, a headstrong little bee, and her best friend Willi, rescue an ant princess they find themselves in the middle of an epic bug battle that will take them to strange new worlds and test their friendship to its limits.", R.drawable.image_4, SimpleDateFormat("yyyy-MM-dd").parse("2021-01-07")!!, "Maya the Bee: The Golden Orb", 6.5f, 94),
            )
    }
}