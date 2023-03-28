package com.example.easyflight.feature_flight.presentation.flights.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.easyflight.feature_flight.presentation.flights.FlightsEvent
import com.example.easyflight.feature_flight.presentation.flights.FlightsViewModel
import com.example.easyflight.ui.theme.Background
import com.example.easyflight.ui.theme.GrayBorder
import com.example.easyflight.ui.theme.GraySoft

@Composable
fun PassengersButton(viewModel: FlightsViewModel) {

    val numPassengers = remember { mutableStateOf(1) }

    OutlinedButton(
        modifier = Modifier.padding(top = 30.dp),
        border = BorderStroke(1.dp, GrayBorder),
        shape = RoundedCornerShape(15),
        colors = ButtonDefaults.buttonColors(backgroundColor = Background),
        onClick = { viewModel.onEvent(FlightsEvent.ShowBottomSheet) }
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "person icon",
            tint = GraySoft
        )
        Text(text = getPassengersText(numPassengers.value))
    }
}



fun getPassengersText(numPassengers: Int): String {
    return if (numPassengers > 1) "$numPassengers Viajeros"
    else "$numPassengers Viajero"
}
