package com.example.easyflight.feature_flight.presentation.flights.components.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easyflight.feature_flight.presentation.flights.components.result.adapters.FlightMainInfo
import com.example.easyflight.ui.theme.ComponentBackground

@Composable
fun FlightMainInfoBox(flight: FlightMainInfo) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(
                color = ComponentBackground,
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Row(Modifier.fillMaxWidth()) {
            Row {
                Column {
                    LegMainInfoBox(flight.leg1)
                    LegMainInfoBox(flight.leg2)
                }
                Column {
                    Text(
                        buildAnnotatedString {
                            withStyle(
                                style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            ) {
                                append("${flight.price} €")
                            }
                        }
                    )
                    Text(
                        buildAnnotatedString {
                            val displayText =
                                if (flight.morePrices) "Varias tarifas" else "Económica"

                            withStyle(style = SpanStyle(fontSize = 12.sp, color = Color.Gray)) {
                                append(displayText)
                            }

                        }
                    )
                }

            }


        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewMainInfo() {

   // FlightMainInfoBox()
}

