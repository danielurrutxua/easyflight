package com.example.easyflight.feature_flight.presentation.flights.components.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.easyflight.ui.theme.ComponentBackground

@Composable
fun FlightMainInfoBox() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                color = ComponentBackground,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Column(Modifier.fillMaxWidth()) {
            Row {
               LegMainInfoBox()
            }

        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMainInfo() {

    FlightMainInfoBox()
}