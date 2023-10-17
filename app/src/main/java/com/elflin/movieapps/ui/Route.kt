package com.elflin.movieapps.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.elflin.movieapps.ui.view.ListMovieView
import com.elflin.movieapps.ui.view.MovieDetailView
import com.elflin.movieapps.ui.view.ProfileView
import com.elflin.movieapps.viewmodel.ListMovieUIState
import com.elflin.movieapps.viewmodel.ListMovieViewModel
import com.elflin.movieapps.viewmodel.MovieDetailUiState
import com.elflin.movieapps.viewmodel.MovieDetailViewModel

enum class ListScreen(){
    ListMovie,
    MovieDetail,
    Profile,
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieAppsRoute(){

    val navController = rememberNavController()
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    Scaffold(

    ) {innerPadding ->
        NavHost(
            navController = navController,
            startDestination = ListScreen.ListMovie.name,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(ListScreen.ListMovie.name){
                val listMovieViewModel: ListMovieViewModel = viewModel()
                val status = listMovieViewModel.listMovieUIState
                when(status){
                    is ListMovieUIState.Loading -> {}
                    is ListMovieUIState.Success -> ListMovieView(
                        movieList = status.data,
                        onFavClicked = {movie ->
                            listMovieViewModel.onFavClicked(movie)
                        },
                        onCardClick = {
                            navController.navigate(ListScreen.MovieDetail.name+"/"+it.id)
                        }
                    )
                    is ListMovieUIState.Error ->{}
                }
            }
            composable(ListScreen.MovieDetail.name+"/{movieId}"){
                val movieDetailViewModel: MovieDetailViewModel = viewModel()
                movieDetailViewModel.getMovieById(
                    it.arguments?.getString("movieId")!!.toInt())

                val status = movieDetailViewModel.movieDetailUiState
                when(status){
                    is MovieDetailUiState.Loading -> {}
                    is MovieDetailUiState.Success -> {
                        MovieDetailView(
                            movie = status.data,
                            onFavClicked = {}
                        )
                    }
                    is MovieDetailUiState.Error -> {}
                }
            }
            composable(ListScreen.Profile.name){
                ProfileView()
            }
        }
    }

}