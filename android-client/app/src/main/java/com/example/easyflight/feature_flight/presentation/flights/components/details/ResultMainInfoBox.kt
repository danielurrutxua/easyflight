package com.example.easyflight.feature_flight.presentation.flights.components.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easyflight.feature_flight.domain.model.service.request.FlightSearchRequest
import com.example.easyflight.feature_flight.domain.model.service.response.Result
import com.example.easyflight.feature_flight.presentation.flights.components.getPassengersText
import com.example.easyflight.ui.theme.ComponentBackground
import com.example.easyflight.ui.theme.GrayText

@Composable
fun ResultMainInfoBox(request: FlightSearchRequest, data: Result) {
    Box(
        Modifier
            .fillMaxWidth()
            .padding(20.dp)
            .background(ComponentBackground, shape = RoundedCornerShape(8.dp)),
    ) {
        Column(Modifier.fillMaxWidth()) {
            Text(
                text = getDestinationName(data),
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(10.dp))
            DatesBox(start = request.departureDate, end = request.returnDate)
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = getAdditionalInfoText(request),
                color = GrayText,
                fontSize = 13.sp
            )
        }

    }
}

fun getRoundTripText(roundTrip: Boolean) = if (roundTrip) "Ida y vuelta" else "Ida"
fun getAdditionalInfoText(request: FlightSearchRequest) =
    "${getPassengersText(request.numPassengers.toInt())} · ${getRoundTripText(request.roundTrip)} · Económico"
fun getDestinationName(data: Result): String =
    data.legs.first().segments.last().arrival.airport.name


