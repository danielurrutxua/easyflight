package com.example.easyflight.feature_flight.presentation.flights.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun AddNumButton(drawableId: Int, onClick: () -> Unit) {
    IconButton(
        modifier = Modifier
            .background(color = Color.White, shape = RoundedCornerShape(5.dp))
            .size(23.dp),
        onClick = onClick,
    ) {
        Icon(
            modifier = Modifier.size(14.dp),
            painter = painterResource(drawableId),
            contentDescription = "My Icon"
            //tint = Color(android.graphics.Color.parseColor("#9ba8b0"))
        )
    }
}