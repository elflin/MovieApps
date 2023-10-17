package com.elflin.examplemovieapps.ui

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.elflin.examplemovieapps.R
import com.elflin.examplemovieapps.ui.view.ListMoviesView
import com.elflin.examplemovieapps.ui.view.LoadingView
import com.elflin.examplemovieapps.ui.view.MovieDetailView
import com.elflin.examplemovieapps.ui.view.ProfileView
import com.elflin.examplemovieapps.ui.view.SearchView
import com.elflin.examplemovieapps.viewmodel.ListMovieUiState
import com.elflin.examplemovieapps.viewmodel.ListMovieViewModel
import com.elflin.examplemovieapps.viewmodel.MovieDetailUiState
import com.elflin.examplemovieapps.viewmodel.MovieDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBarMovieApps(
    scrollBehavior: TopAppBarScrollBehavior,
    contextToast: Context,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary,
        ),
        title = {
            Text(
                text = "Movie Apps",
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
        },
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            }else {
                IconButton(onClick = {
                    Toast.makeText(contextToast, "Menu Clicked", Toast.LENGTH_LONG).show()
                }) {
                    Icon(
                        imageVector = Icons.Filled.Menu,
                        contentDescription = "Localized description"
                    )
                }
            }
        },
        actions = {
            if (!canNavigateBack) {
                IconButton(onClick = { /* do something */ }) {
                    Icon(
                        imageVector = Icons.Filled.Logout,
                        contentDescription = "Logout"
                    )
                }
            }
        },
        scrollBehavior = scrollBehavior
    )
}

@Composable
fun BottomNavBarMovieApps(navController:NavController){

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val items = listOf(
        BottomNavItem.Home,
        BottomNavItem.Search,
        BottomNavItem.Profile
    )

    NavigationBar{
        items.forEach{ item->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon), 
                        contentDescription = item.title
                    ) 
                },
                label = {
                    Text(text = item.title)
                },
                selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                onClick = {
                    navController.navigate(item.route){
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}


sealed class BottomNavItem(var title:String, var icon:Int, var route:String){
    object Home : BottomNavItem("Home", R.drawable.ic_home, MovieAppsScreen.ListMovie.name)
    object Search: BottomNavItem("Search",R.drawable.ic_search, MovieAppsScreen.Search.name)
    object Profile: BottomNavItem("Profile",R.drawable.ic_profile,MovieAppsScreen.Profile.name)
}

enum class MovieAppsScreen(){
    Register,
    Login,
    Search,
    ListMovie,
    MovieDetail,
    Profile
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieAppsRoute(){

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    val navController = rememberNavController()
    val contextToast = LocalContext.current
    var canNavigateBack by remember {mutableStateOf(navController.previousBackStackEntry != null) }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopBarMovieApps(
                scrollBehavior = scrollBehavior,
                contextToast = contextToast,
                canNavigateBack = canNavigateBack,
                navigateUp = {navController.navigateUp()}
            ) },
        bottomBar = {
            if (!canNavigateBack) {
                BottomNavBarMovieApps(navController)
            }
        },
    ) {innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MovieAppsScreen.ListMovie.name,
            modifier = Modifier.padding(innerPadding)
        ){
            // Route to Home (List Movie)
            composable(MovieAppsScreen.ListMovie.name){
                val listMovieViewModel: ListMovieViewModel = viewModel()
                val status = listMovieViewModel.listMovieUiState
                when (status) {
                    is ListMovieUiState.Loading -> LoadingView()
                    is ListMovieUiState.Success -> ListMoviesView(
                        movieList = status.data,
                        onFavClicked = {movie ->
                            listMovieViewModel.onFavClicked(movie)
                        },
                        onCardClicked = {
                            navController.navigate(MovieAppsScreen.MovieDetail.name+"/"+it.id)
                        }
                    )
                    is ListMovieUiState.Error -> {}
                }
            }

            // Route to Profile
            composable(MovieAppsScreen.Profile.name){
                ProfileView()
            }

            // Route to Search
            composable(MovieAppsScreen.Search.name){
                SearchView()
            }

            // Route to Movie Detail
            composable(MovieAppsScreen.MovieDetail.name+"/{movieId}") { backStackEntry ->
                val movieDetailViewModel: MovieDetailViewModel = viewModel()
                movieDetailViewModel.getMoviebyId(
                    backStackEntry.arguments?.getString("movieId")!!.toInt()
                )

                val status = movieDetailViewModel.movieDetailUiState
                when (status) {
                    is MovieDetailUiState.Loading -> LoadingView()
                    is MovieDetailUiState.Success -> {
                        MovieDetailView(movie = status.data)
                        canNavigateBack = navController.previousBackStackEntry != null
                    }
                    is MovieDetailUiState.Error -> {}
                }
            }
        }
    }
}