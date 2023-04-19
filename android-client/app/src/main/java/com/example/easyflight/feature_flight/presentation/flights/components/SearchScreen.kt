package com.example.easyflight.feature_flight.presentation.flights.components

import androidx.compose.foundation.background
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
import com.example.easyflight.feature_flight.presentation.flights.FlightsViewModel
import com.example.easyflight.feature_flight.presentation.flights.LoadingScreen
import com.example.easyflight.feature_flight.presentation.flights.components.search.SearchContainer
import com.example.easyflight.ui.theme.Background

@Composable
fun SearchScreen(
    viewModel: FlightsViewModel,
    onNavigateToResults: () -> Unit
) {
    // Agrega esta parte para manejar el estado de las pestañas
    val tabs = listOf("Ida y vuelta", "Ida")
    var selectedTabIndex by remember { mutableStateOf(0) }

    // Cuando searchResults deja de estar vacío, navega a la segunda pantalla
    LaunchedEffect(viewModel.state.value.searchResults) {
        if (viewModel.state.value.searchResults.isNotEmpty()) {
            onNavigateToResults()
        }
    }

    if(viewModel.state.value.isLoading){
        LoadingScreen()
    } else{
        Column(
            Modifier
                .fillMaxSize()
                .background(Background)) {
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
