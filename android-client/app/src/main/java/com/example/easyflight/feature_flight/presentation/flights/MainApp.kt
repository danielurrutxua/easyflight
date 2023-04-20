package com.example.easyflight.feature_flight.presentation.flights

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.easyflight.feature_flight.domain.model.service.request.FlightSearchRequest
import com.example.easyflight.feature_flight.domain.model.service.response.Result
import com.example.easyflight.feature_flight.presentation.flights.components.DetailsScreen
import com.example.easyflight.feature_flight.presentation.flights.components.LegsDetailScreen
import com.example.easyflight.feature_flight.presentation.flights.components.ProvidersScreen
import com.example.easyflight.feature_flight.presentation.flights.components.ResultsScreen
import com.example.easyflight.feature_flight.presentation.flights.components.SearchScreen
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.LocalDate

@Composable
fun MainApp() {
    val navController = rememberNavController()

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var resultMap by remember { mutableStateOf<Map<String, List<Result>>?>(null) }
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
/***
        composable("results") {
            val navigateToDetails: (Int, Int) -> Unit = { selectedTabIndex, itemIndex ->
                navController.navigate("details/$selectedTabIndex/$itemIndex")
            }


            // Llama a readJsonFile en un CoroutineScope
            readJsonFile(context, coroutineScope) { result ->
                resultMap = result
            }

            // Inicializa ResultsScreen solo si resultMap no es null
            if (resultMap != null) {
                ResultsScreen(
                    data = resultMap!!,
                    onBack = { navController.popBackStack() },
                    request = getSampleRequest(),
                    navigateToDetails = navigateToDetails
                )
            }

        }
**/
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
            val options = findResultById(resultMap!!, resultId!!)!!.options

            ProvidersScreen(options) { navController.popBackStack() }
        }
        composable("legs/{resultId}") { backStackEntry ->
            val resultId = backStackEntry.arguments?.getString("resultId")
            val result = findResultById(resultMap!!, resultId!!)!!

            LegsDetailScreen(result) { navController.popBackStack() }
        }

    }

}

fun getSampleRequest() = FlightSearchRequest(
    origin = "BIO",
    destination = "JFK",
    departureDate = LocalDate.now(),
    returnDate = LocalDate.now().plusDays(5),
    numPassengers = "2",
    roundTrip = true
)

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


fun readJsonFile(
    context: Context,
    scope: CoroutineScope,
    callback: (Map<String, List<Result>>) -> Unit
) {
    scope.launch {
        val assetManager = context.assets
        val inputStream = assetManager.open("result.json")
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))
        val jsonString = bufferedReader.use { it.readText() }

        val gson = Gson()
        val mapType = object : TypeToken<Map<String, List<Result>>>() {}.type
        val resultMap = gson.fromJson<Map<String, List<Result>>>(jsonString, mapType)

        callback(resultMap)
    }
}