package com.patronas.flickr.presentation.ui.screens.main_activity.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.patronas.flickr.presentation.ui.screens.screens.details.DetailsScreen
import com.patronas.flickr.presentation.ui.screens.screens.home.HomeScreen
import com.patronas.flickr.presentation.ui.screens.screens.home.MainViewModel

@Composable
fun NavigationComponent(
    navController: NavHostController,
    mainViewModel: MainViewModel,
    startService: () -> Unit
) {

    NavHost(
        navController = navController,
        startDestination = Route.home
    ) {
        composable(Route.home) {
            HomeScreen(
                viewModel = mainViewModel,
                startService = { startService() },
                navigateToDetails = {
                    navController.navigateToScreenWithParam(screen = Route.details, param = it)
                }
            )
        }
        composable(Route.imageDetailsRoute) {
            val photos = mainViewModel.photos.collectAsState().value
            val id = it.arguments?.getString(Argument.imageDetailsId)
            val photo = photos.find { it.id == id }
            photo?.let {
                DetailsScreen(photo = it)
            }
        }
    }
}