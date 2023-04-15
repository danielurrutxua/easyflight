package com.example.easyflight.feature_flight.presentation.flights

import android.content.Context
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.easyflight.feature_flight.domain.model.service.response.Result
import com.example.easyflight.feature_flight.presentation.flights.components.ResultsScreen
import com.example.easyflight.feature_flight.presentation.flights.components.SearchScreen
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader

@Composable
fun MainApp() {
    val navController = rememberNavController()

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    var resultMap by remember { mutableStateOf<Map<String, List<Result>>?>(null) }
    val viewModel: FlightsViewModel = hiltViewModel()
    NavHost(navController, startDestination = "results") {
        composable("search") {
            SearchScreen(viewModel) { navController.navigate("results") }
        }
//        composable("results") {
//            ResultsScreen(viewModel.state.value.searchResults)
//        }

        composable("results") {
            // Llama a readJsonFile en un CoroutineScope


            // Llama a readJsonFile en un CoroutineScope
            readJsonFile(context, coroutineScope) { result ->
                resultMap = result
            }

            // Inicializa ResultsScreen solo si resultMap no es null
            if (resultMap != null) {
                ResultsScreen(resultMap!!)
            }

        }

    }

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