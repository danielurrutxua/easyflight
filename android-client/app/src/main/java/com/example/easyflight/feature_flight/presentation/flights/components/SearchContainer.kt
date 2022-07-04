package com.example.easyflight.feature_flight.presentation.flights.components

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun SearchContainer() {


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
            PassengersButton()

        }
        SimpleButton(text = "Buscar vuelos", onClick = { /*TODO*/ })
    }


}


