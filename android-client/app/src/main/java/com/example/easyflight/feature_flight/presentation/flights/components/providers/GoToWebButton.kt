package com.example.easyflight.feature_flight.presentation.flights.components.providers

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.easyflight.ui.theme.GrayButton

@Composable
fun GoToWebButton(url: String) {
    val context = LocalContext.current

    Button(
        onClick = { openUrlInBrowser(context, url) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = GrayButton,
            contentColor = Color.White
        ),
        shape = MaterialTheme.shapes.medium,
        elevation = ButtonDefaults.elevation(defaultElevation = 0.dp, pressedElevation = 0.dp)
    ) {
        Text(text = "Ir al sitio web")
    }
}

fun openUrlInBrowser(context: Context, url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    if (intent.resolveActivity(context.packageManager) != null) {
        context.startActivity(intent)
    } else {
        // Manejar el caso en el que no hay ninguna aplicación disponible para manejar la URL
    }
}