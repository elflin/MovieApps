package com.elflin.examplemovieapps.ui.view

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.elflin.examplemovieapps.model.Movie
import com.elflin.examplemovieapps.viewmodel.ListMovieUiState
import com.elflin.examplemovieapps.viewmodel.ListMovieViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ListMoviesView(
    movieList: List<Movie>,
    onFavClicked: (Movie) -> Unit,
    onCardClicked: (Movie)-> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        modifier = modifier,
        columns = GridCells.Fixed(2)
    ) {
        items(movieList) { movie ->
            var isLikedView by remember { mutableStateOf(movie.isLiked) }
            MovieCard(
                movie = movie,
                onFavClicked = {
                    onFavClicked(movie)
                    isLikedView = movie.isLiked
                },
                onCardClicked = { onCardClicked(movie) },
                isLikedView = isLikedView,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieCard(
    movie: Movie,
    onFavClicked: () -> Unit,
    onCardClicked: () -> Unit,
    isLikedView: Boolean,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        onClick = onCardClicked
    ) {
        Column {
            Box(
                contentAlignment = Alignment.BottomEnd
            ) {
                Image(
                    painter = painterResource(id = movie.poster_path),
                    contentDescription = "Movie image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp),
                    contentScale = ContentScale.FillWidth
                )
                FloatingActionButton(
                    onClick = onFavClicked,
                    shape = CircleShape,
                    modifier = Modifier.padding(end = 4.dp, bottom = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "Heart",
                        tint = if (isLikedView) {
                            Color(0xFFEC407A)
                        } else {
                            Color(0xFF9C9C9C)
                        }
                    )
                }
            }
            Row(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp),
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = movie.title,
                    modifier = Modifier
                        .weight(4f)
                        .height(50.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2
                )
                Text(
                    text = "(${movie.getYear()})",
                    modifier = Modifier
                        .weight(2f),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Right
                )
            }
            Row(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Star",
                    tint = Color(0xFFFDCC0D)
                )
                Text(
                    text = movie.vote_average.toString() + "/10.0",
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
            Text(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                text = movie.overview,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Left,
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
private fun ListMoviesPreview() {
    val listMovieViewModel: ListMovieViewModel = viewModel()
    val status = listMovieViewModel.listMovieUiState
    when (status) {
        is ListMovieUiState.Loading -> {}
        is ListMovieUiState.Success -> ListMoviesView(
            movieList = status.data,
            onFavClicked = { listMovieViewModel.onFavClicked(it) },
            onCardClicked = {}
        )
        is ListMovieUiState.Error -> {}
    }
}


