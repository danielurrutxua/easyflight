package com.example.easyflight.feature_flight.presentation.flights

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.easyflight.feature_flight.presentation.flights.components.ResultsScreen
import com.example.easyflight.feature_flight.presentation.flights.components.SearchScreen

@Composable
fun MainApp() {
    val navController = rememberNavController()
    val viewModel: FlightsViewModel = hiltViewModel()
        NavHost(navController, startDestination = "search") {
            composable("search") {
                SearchScreen(viewModel, navController)
            }
            composable("results") {
                ResultsScreen(viewModel.state.value.searchResults)
            }
        }

}