package com.example.easyflight.feature_flight.presentation.flights.components.result

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import com.example.easyflight.ui.theme.GrayText

@Composable
fun HoursText(timeO: String, timeD: String, nextDay: Boolean) {
    Text(
        buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            ) {
                append(getTimesText(timeO, timeD))
            }
            if (nextDay) {
                withStyle(
                    style = SpanStyle(
                        fontSize = 12.sp,
                        color = GrayText
                    )
                ) {
                    append(" +1")
                }
            }
        }
    )
}