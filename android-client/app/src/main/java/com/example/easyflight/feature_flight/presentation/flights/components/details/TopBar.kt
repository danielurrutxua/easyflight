package com.example.easyflight.feature_flight.presentation.flights.components.details

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.easyflight.ui.theme.ComponentBackground

@Composable
fun TopBar(onBack: () -> Unit) {
    TopAppBar(
        backgroundColor = ComponentBackground,
        elevation = 0.dp,
        title = {
            Text(
                text = "Detalles de tu viaje",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver atrás",
                    tint = Color.White
                )
            }
        }
    )
}