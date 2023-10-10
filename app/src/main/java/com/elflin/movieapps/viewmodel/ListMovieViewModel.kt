package com.elflin.movieapps.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elflin.movieapps.data.DataSource
import com.elflin.movieapps.model.Movie
import kotlinx.coroutines.launch

sealed interface ListMovieUIState{
    data class Success(val data: List<Movie>):ListMovieUIState
    object Error: ListMovieUIState
    object Loading: ListMovieUIState
}

class ListMovieViewModel: ViewModel() {
    var listMovieUIState: ListMovieUIState by mutableStateOf(ListMovieUIState.Loading)
        private set

    private lateinit var data: List<Movie>

    init{
        loadData()
    }

    fun loadData(){
        viewModelScope.launch{
            try {
                data = DataSource().loadMovie()
                listMovieUIState = ListMovieUIState.Success(data)
            }catch(e: Exception){
                listMovieUIState = ListMovieUIState.Error
            }
        }
    }

    fun onFavClicked(movie: Movie){
        movie.isLiked = !movie.isLiked
        // sent server updated movie to server
    }
}