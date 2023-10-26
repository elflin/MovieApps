package com.elflin.movieapps.ui.view

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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.elflin.movieapps.R
import com.elflin.movieapps.data.DataSource
import com.elflin.movieapps.model.Movie
import com.elflin.movieapps.repository.MovieDBContainer
import com.elflin.movieapps.viewmodel.ListMovieUIState
import com.elflin.movieapps.viewmodel.ListMovieViewModel

@Composable
fun ListMovieView(
    movieList: List<Movie>,
    onFavClicked: (Movie) -> Unit,
    onCardClick: (Movie) -> Unit
){
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
    ){
        items(movieList){movie ->

            var isLikedView by rememberSaveable { mutableStateOf(movie.isLiked)}

            MovieCard(
                movie = movie,
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxSize(),
                onFavClicked = {
                    onFavClicked(movie)
                    isLikedView = movie.isLiked
                },
                onCardClicked = {onCardClick(movie)},
                isLikedView = isLikedView
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieCard(
    movie: Movie,
    modifier: Modifier = Modifier,
    onFavClicked: () -> Unit,
    onCardClicked: () -> Unit,
    isLikedView: Boolean
){
    Card(
        modifier = modifier,
        onClick = onCardClicked
    ) {
        Column {
            Box(
                contentAlignment = Alignment.BottomEnd
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context = LocalContext.current)
                        .data(MovieDBContainer.BASE_IMG + movie.poster_path)
                        .crossfade(true)
                        .build(),
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
                        contentDescription = "Favorite",
                        tint = if(isLikedView){
                            Color(0xFFEC407A)
                        }else{
                            Color(0xFF636363)
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
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(2f)
                        .height(60.dp)
                )

                Text(
                    text = "(${movie.getYear()})",
                    textAlign = TextAlign.Right,
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ){
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Star",
                    tint = Color(0xFFFDCC0D)
                )
                Text(
                    text = "${movie.vote_average}/10.0",
                    modifier = Modifier.padding(start = 4.dp)
                )
            }

            Text(
                text = movie.overview,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Left
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun ListMoviePreview(){
    val listMovieViewModel: ListMovieViewModel = viewModel()
    val status = listMovieViewModel.listMovieUIState
    when(status){
        is ListMovieUIState.Loading -> {}
        is ListMovieUIState.Success -> ListMovieView(
            movieList = status.data,
            onFavClicked = {movie ->
                listMovieViewModel.onFavClicked(movie)
            },
            {}
        )
        is ListMovieUIState.Error ->{}
    }
}