package com.example.easyflight.feature_flight.presentation.flights.components.result

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easyflight.feature_flight.presentation.flights.components.result.adapters.FlightMainInfo
import com.example.easyflight.ui.theme.ComponentBackground

@Composable
fun FlightMainInfoBox(flight: FlightMainInfo, navigateToDetails: () -> Unit) {

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(PaddingValues(start = 8.dp, top = 8.dp, end = 8.dp))
            .background(
                color = ComponentBackground,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(
                onClick = { navigateToDetails() }
            )
    ){
        Column(
            Modifier
                .fillMaxWidth()
                .padding(PaddingValues(start = 20.dp, top = 20.dp, end = 20.dp, bottom = 10.dp))
        ) {
            LegMainInfoBox(flight.leg1)
            LegMainInfoBox(flight.leg2)

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                modifier = Modifier.align(Alignment.End),
                text = "${flight.price} €",
                color = White,
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }


}


@Preview(showBackground = true)
@Composable
fun PreviewMainInfo() {

    // FlightMainInfoBox()
}

