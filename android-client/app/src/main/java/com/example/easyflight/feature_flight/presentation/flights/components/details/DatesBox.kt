package com.example.easyflight.feature_flight.presentation.flights.components.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easyflight.feature_flight.presentation.util.dateVisualizerFormat
import java.time.LocalDate

@Composable
fun DatesBox(start: LocalDate, end: LocalDate?) {
    Box(
        modifier = Modifier
            .background(Color.White, shape = RoundedCornerShape(4.dp))
            .padding(horizontal = 4.dp, vertical = 3.dp)
    ) {
        Text(
            text = getDatesString(start, end),
            color = Color.Black,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp
        )
    }
}

fun getDatesString(start: LocalDate, end: LocalDate?) =
    if (end != null) "${start.dateVisualizerFormat()} - ${end.dateVisualizerFormat()}"
    else start.dateVisualizerFormat()

