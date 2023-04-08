package com.example.easyflight.feature_flight.presentation.flights.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easyflight.R
import com.example.easyflight.ui.theme.GraySoft

@Composable
fun AddPassengerRow(
    passengerType: String,
    ageDescription: String,
    value: MutableState<Int>
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(45.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.Bottom) {
            Text(passengerType, color = Color.White)
            Text(
                modifier = Modifier.padding(PaddingValues(start = 15.dp)),
                text = ageDescription,
                color = GraySoft,
                fontSize = 13.sp
            )
        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            AddNumButton(R.drawable.minus, onClick = {
                val min = if (passengerType == "Adultos") 1 else 0
                value.value = if (value.value == min) value.value else value.value - 1
            })
            Text(
                modifier = Modifier.padding(PaddingValues(horizontal = 10.dp)),
                text = value.value.toString(),
                color = Color.White
            )
            AddNumButton(
                R.drawable.plus,
                onClick = { value.value = if (value.value == 10) value.value else value.value + 1 })

        }
    }
}

