package com.example.easyflight.feature_flight.presentation.flights.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.easyflight.feature_flight.presentation.flights.FlightsViewModel
import com.example.easyflight.ui.theme.Background

@Composable
fun SearchWindow(viewModel: FlightsViewModel = hiltViewModel()) {
    // Agrega esta parte para manejar el estado de las pestañas
    val tabs = listOf("Ida y vuelta", "Ida")
    var selectedTabIndex by remember { mutableStateOf(0) }

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
