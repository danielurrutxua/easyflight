package com.example.easyflight.feature_flight.presentation.flights.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout

@Composable
fun AirportsSelector() {

    val airportsRef = ConstrainedLayoutReference("airports")
    val exchangeButtonRef = ConstrainedLayoutReference("exchangeButton")

    ConstraintLayout(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(modifier = Modifier
            .constrainAs(airportsRef) {
                top.linkTo(parent.top)
            }) {
            SimpleTextField(
                text = "Madrid, España",
                label = "Origen",
                cornerSizes = CornerSizes(15, 15, 0, 0),
                icon = Icons.Filled.ThumbUp
            )
            SimpleTextField(
                label = "Destino",
                cornerSizes = CornerSizes(0, 0, 15, 15),
                icon = Icons.Default.Face
            )
        }

        ExchangeAirportsButton(modifier = Modifier
            .width(30.dp)
            .height(30.dp)
            .constrainAs(exchangeButtonRef) {
                end.linkTo(airportsRef.end, margin = 40.dp)
                top.linkTo(airportsRef.top)
                bottom.linkTo(airportsRef.bottom)

            })

    }
}