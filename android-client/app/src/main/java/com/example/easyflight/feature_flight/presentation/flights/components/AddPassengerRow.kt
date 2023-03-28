package com.example.easyflight.feature_flight.presentation.flights.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easyflight.ui.theme.GraySoft

@Composable
fun AddPassengerRow(passengerType: String, ageDescription: String, defaultNum: Int = 0) {

    val num by remember { mutableStateOf(defaultNum)}

    Row(
        modifier = Modifier.fillMaxWidth().height(45.dp),
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
            AddNumButton(Icons.Default.Add, onClick = { /*TODO*/ })
            Text(
                modifier = Modifier.padding(PaddingValues(horizontal = 10.dp)),
                text = num.toString(),
                color = Color.White
            )
            AddNumButton(Icons.Default.Add, onClick = { /*TODO*/ })

        }
    }
}