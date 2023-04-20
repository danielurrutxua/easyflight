package com.example.easyflight.feature_flight.presentation.flights.components.legs.segments

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp

@Composable
fun LineWithCircles() {
    Box(modifier = Modifier.padding(8.dp)) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface(
                color = Color.White,
                shape = CircleShape,
                modifier = Modifier.size(5.dp)
            ) {}

            Spacer(modifier = Modifier.height(5.dp))

            Canvas(modifier = Modifier.height(92.dp).width(1.dp)) {
                drawLine(
                    start = Offset(x = size.width / 2, y = 0f),
                    end = Offset(x = size.width / 2, y = size.height),
                    color = Color.White,
                    strokeWidth = 1.dp.toPx(),
                    cap = StrokeCap.Round
                )
            }

            Spacer(modifier = Modifier.height(5.dp))

            Surface(
                color = Color.White,
                shape = CircleShape,
                modifier = Modifier.size(5.dp)
            ) {}
        }
    }
}