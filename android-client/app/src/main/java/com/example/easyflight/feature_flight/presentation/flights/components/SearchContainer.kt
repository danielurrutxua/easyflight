package com.example.easyflight.feature_flight.presentation.flights.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.easyflight.feature_flight.presentation.flights.FlightsViewModel


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchContainer(viewModel: FlightsViewModel = hiltViewModel()) {

    BottomSheetScaffold(
        sheetContent = { AddPassengersBox(viewModel) }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    20.dp
                ), verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                AirportsSelector()
                DatesVisualizer()
                PassengersButton(viewModel)

            }

            SimpleButton(text = "Buscar vuelos", onClick = { /*TODO*/ })
        }
    }
}



