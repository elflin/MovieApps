package com.elflin.movieapps.repository

import android.util.Log
import com.elflin.movieapps.model.Movie
import com.elflin.movieapps.service.MovieDBServices
import java.text.SimpleDateFormat

class MovieDBRepositories(private val movieDBServices: MovieDBServices) {

    suspend fun getAllMovie(page: Int = 1):List<Movie>{
        try{
            val ListRawMovie = movieDBServices.getAllMovie().results

            val data = mutableListOf<Movie>()

            for (rawMovie in ListRawMovie){
                val movie = Movie(
                    rawMovie.id,
                    rawMovie.overview,
                    rawMovie.poster_path,
                    SimpleDateFormat("yyyy-MM-dd").parse(rawMovie.release_date),
                    rawMovie.title,
                    rawMovie.vote_average.toFloat(),
                    rawMovie.vote_count
                )

                data.add(movie)
            }

            return data
        }catch (e: Exception){
            Log.d("Error11", e.message.toString())
            return mutableListOf()
        }
    }

    suspend fun getMovieDetail(movieId: Int): Movie{
        val rawMovie = movieDBServices.getMovieDetail(movieId)
        Log.d("Test123", rawMovie.toString())
        val data = Movie(
            rawMovie.id,
            rawMovie.overview,
            rawMovie.poster_path,
            SimpleDateFormat("yyyy-MM-dd").parse(rawMovie.release_date),
            rawMovie.title,
            rawMovie.vote_average.toFloat(),
            rawMovie.vote_count
        )

        return data
    }

}