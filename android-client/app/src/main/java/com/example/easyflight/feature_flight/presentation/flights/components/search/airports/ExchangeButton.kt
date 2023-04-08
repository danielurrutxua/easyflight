package com.example.easyflight.feature_flight.presentation.flights.components

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.easyflight.R
import com.example.easyflight.feature_flight.presentation.flights.FlightsEvent
import com.example.easyflight.ui.theme.Background
import com.example.easyflight.ui.theme.ComponentBackground

@Composable
fun ExchangeAirportsButton(modifier: Modifier, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = modifier
            .size(45.dp),
        shape = RoundedCornerShape(5.dp),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = ComponentBackground,
            contentColor = Color(android.graphics.Color.parseColor("#9ba8b0"))
        ),
        elevation = ButtonDefaults.elevation(
            defaultElevation = 0.dp,
            pressedElevation = 4.dp
        ),
        border = BorderStroke(1.dp, Background),
        contentPadding = PaddingValues(0.dp)
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                painter = painterResource(id = R.drawable.swap_vertical),
                contentDescription = "Exchange airports",
                modifier = Modifier.size(20.dp)
            )
        }
    }
}