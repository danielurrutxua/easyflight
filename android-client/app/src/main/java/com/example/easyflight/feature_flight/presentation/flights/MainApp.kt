package com.example.easyflight.feature_flight.presentation.flights

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.easyflight.feature_flight.domain.model.service.response.Result
import com.example.easyflight.feature_flight.presentation.flights.components.DetailsScreen
import com.example.easyflight.feature_flight.presentation.flights.components.LegsDetailScreen
import com.example.easyflight.feature_flight.presentation.flights.components.ProvidersScreen
import com.example.easyflight.feature_flight.presentation.flights.components.ResultsScreen
import com.example.easyflight.feature_flight.presentation.flights.components.SearchScreen

@Composable
fun MainApp() {
    val navController = rememberNavController()

    val viewModel = hiltViewModel<FlightsViewModel>()
    NavHost(navController, startDestination = "search") {
        composable("search") {
            SearchScreen(viewModel) { navController.navigate("results") }
        }
        composable("results") {
            val navigateToDetails: (Int, Int) -> Unit = { selectedTabIndex, itemIndex ->
                navController.navigate("details/$selectedTabIndex/$itemIndex")
            }

            ResultsScreen(
                viewModel.state.value.searchResults,
                request = viewModel.state.value.searchRequest!!,
                onBack = { navController.popBackStack() },
                navigateToDetails = navigateToDetails
            )
        }

        composable("details/{selectedTabIndex}/{itemIndex}") { backStackEntry ->
            val selectedTabIndex =
                backStackEntry.arguments?.getString("selectedTabIndex")?.toIntOrNull() ?: 0
            val itemIndex = backStackEntry.arguments?.getString("itemIndex")?.toIntOrNull() ?: 0

            val openProviders: (String) -> Unit = { resultId ->
                navController.navigate("providers/$resultId")
            }

            val openLegsDetail: (String) -> Unit = { resultId ->
                navController.navigate("legs/$resultId")
            }

            DetailsScreen(
                request = viewModel.state.value.searchRequest!!,
                data = getResultItem(viewModel.state.value.searchResults, selectedTabIndex, itemIndex),
                onBack = { navController.popBackStack() },
                onOpenProviders = openProviders,
                onOpenLegsDetail = openLegsDetail
            )


        }
        composable("providers/{resultId}") { backStackEntry ->
            val resultId = backStackEntry.arguments?.getString("resultId")
            val options = findResultById(viewModel.state.value.searchResults, resultId!!)!!.options

            ProvidersScreen(options) { navController.popBackStack() }
        }
        composable("legs/{resultId}") { backStackEntry ->
            val resultId = backStackEntry.arguments?.getString("resultId")
            val result = findResultById(viewModel.state.value.searchResults, resultId!!)!!

            LegsDetailScreen(result) { navController.popBackStack() }
        }

    }

}

fun getResultItem(
    resultMap: Map<String, List<Result>>,
    selectedTabIndex: Int,
    itemIndex: Int
): Result {
    val keys = resultMap.keys.toList()
    val selectedKey = keys[selectedTabIndex]
    return resultMap[selectedKey]!![itemIndex]
}

fun findResultById(map: Map<String, List<Result>>?, id: String): Result? {
    if (map == null) {
        return null
    }

    for (entry in map) {
        val result = entry.value.find { it.id == id }
        if (result != null) {
            return result
        }
    }
    return null
}
