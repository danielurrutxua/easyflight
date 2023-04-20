package com.example.easyflight.feature_flight.presentation.flights.components.details

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.easyflight.ui.theme.ComponentBackground

@Composable
fun TopBar(onBack: () -> Unit, text: String, icon: ImageVector, color:Color = ComponentBackground) {
    TopAppBar(
        backgroundColor = color,
        elevation = 0.dp,
        title = {
            Text(
                text = text,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = icon,
                    contentDescription = "Volver atrás",
                    tint = Color.White
                )
            }
        }
    )
}