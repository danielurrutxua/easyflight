package com.example.easyflight.feature_flight.presentation.flights.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.easyflight.R
import com.example.easyflight.feature_flight.presentation.flights.FlightsEvent
import com.example.easyflight.ui.theme.Background
import com.example.easyflight.ui.theme.ComponentBackground

@Composable
fun ExchangeAirportsButton(modifier: Modifier, onClick: () -> Unit) {

    Box(
        modifier = modifier
            .border(
                width = 1.dp,
                color = Background,
                shape = RoundedCornerShape(5.dp)
            )
            .background(color = ComponentBackground)
    )

    {
        IconButton(
            onClick = { onClick() },
        ) {
            Icon(
                painter = painterResource(id = R.drawable.swap_vertical),
                contentDescription = "Exchange airports",
                tint = Color(android.graphics.Color.parseColor("#9ba8b0")),
                modifier = Modifier.size(20.dp)
            )
        }
    }

}