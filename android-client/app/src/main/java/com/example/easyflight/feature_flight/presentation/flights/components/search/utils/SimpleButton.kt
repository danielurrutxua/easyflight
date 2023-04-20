package com.example.easyflight.feature_flight.presentation.flights.components.search.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.easyflight.ui.theme.CyanBlue
import com.example.easyflight.ui.theme.GrayButton

@Composable
fun SimpleButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp).background(CyanBlue),
        colors = ButtonDefaults.buttonColors(
            backgroundColor = CyanBlue,
            contentColor = Color.White
        ),
        shape = MaterialTheme.shapes.medium

    ) {
        Text(text)
    }
}