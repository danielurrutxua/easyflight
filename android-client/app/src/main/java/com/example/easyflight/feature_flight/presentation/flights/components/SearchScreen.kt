package com.example.easyflight.feature_flight.presentation.flights.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.easyflight.feature_flight.presentation.flights.FlightsViewModel
import com.example.easyflight.feature_flight.presentation.flights.LoadingScreen
import com.example.easyflight.ui.theme.Background

@Composable
fun SearchScreen(viewModel: FlightsViewModel, navController: NavController) {
    // Agrega esta parte para manejar el estado de las pestañas
    val tabs = listOf("Ida y vuelta", "Ida")
    var selectedTabIndex by remember { mutableStateOf(0) }

    if(viewModel.state.value.searchResults.isNotEmpty()){
        // Navega a la nueva pantalla para mostrar los resultados
        navController.navigate("results") {
            popUpTo(navController.graph.startDestinationId)
        }
    }

    if(viewModel.state.value.isLoading){
        LoadingScreen()
    } else{
        Column(Modifier.fillMaxSize()) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                backgroundColor = Background,
                contentColor = Color.White,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        color = Color.White
                    )
                }
            ) {
                tabs.forEachIndexed { index, title ->
                    Tab(
                        text = { Text(title) },
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index }
                    )
                }
            }

            // Contenido de las pestañas
            when (selectedTabIndex) {
                0 -> SearchContainer(viewModel, roundTrip = true)
                1 -> SearchContainer(viewModel, roundTrip = false)
            }
        }
    }

}
