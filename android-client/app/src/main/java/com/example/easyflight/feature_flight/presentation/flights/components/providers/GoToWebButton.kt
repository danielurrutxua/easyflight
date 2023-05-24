package com.example.easyflight.feature_flight.presentation.flights.components.providers

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.content.Intent
import android.net.Uri
import com.example.easyflight.ui.theme.GrayButton

@Composable
fun GoToWebButton(url: String) {

    val context = LocalContext.current
    val openURL = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) { result ->

    }

    Button(
        onClick = {
            openURL.launch(Intent(Intent.ACTION_VIEW).apply {

                data = Uri.parse(generateUrl(url))
                // Verifica si hay una aplicación disponible para manejar esta intención
                if (resolveActivity(context.packageManager) != null) {
                    context.startActivity(this)
                } else {
                    // No hay aplicación disponible, puedes mostrar un mensaje de error o hacer otra cosa
                    println("No hay una aplicación para abrir esta URL.")
                }
            })
        },
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

fun generateUrl(url: String): String {
    val SKYSCANNER = "https://www.skyscanner.es"
    val KAYAK = "https://www.kayak.es"

    return if(url.contains("transport")) SKYSCANNER.plus(url)
    else KAYAK.plus(url)
}

