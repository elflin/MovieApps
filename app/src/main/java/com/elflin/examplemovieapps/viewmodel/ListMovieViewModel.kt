package com.elflin.examplemovieapps.viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elflin.examplemovieapps.data.DataSource
import com.elflin.examplemovieapps.model.Movie
import kotlinx.coroutines.launch

sealed interface ListMovieUiState{
    data class Success(val data: List<Movie>): ListMovieUiState
    object Error: ListMovieUiState
    object Loading: ListMovieUiState
}

class ListMovieViewModel: ViewModel() {

    private lateinit var data: List<Movie>
    var listMovieUiState: ListMovieUiState by mutableStateOf(ListMovieUiState.Loading)
        private set

    init {
        loadData()
    }

    @SuppressLint("MutableCollectionMutableState")
    fun loadData(){
        viewModelScope.launch {
            try {
                data = DataSource().loadMovie()
                listMovieUiState = ListMovieUiState.Success(data)
            }catch (e: Exception){
                listMovieUiState = ListMovieUiState.Error
            }

        }
    }

    fun onFavClicked(item: Movie){
        item.isLiked = !item.isLiked
    }
}