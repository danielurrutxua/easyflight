package com.example.easyflight.feature_flight.presentation.flights.components.details

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easyflight.feature_flight.domain.model.service.request.FlightSearchRequest
import com.example.easyflight.feature_flight.domain.model.service.response.Airline
import com.example.easyflight.feature_flight.domain.model.service.response.Leg
import com.example.easyflight.feature_flight.domain.model.service.response.Result
import com.example.easyflight.feature_flight.presentation.flights.components.getHour
import com.example.easyflight.feature_flight.presentation.flights.components.getPassengersText
import com.example.easyflight.feature_flight.presentation.flights.components.nextDay
import com.example.easyflight.feature_flight.presentation.flights.components.result.adapters.LegMainInfo
import com.example.easyflight.ui.theme.Background
import com.example.easyflight.ui.theme.ComponentBackground
import com.example.easyflight.ui.theme.CyanBlue
import com.example.easyflight.ui.theme.GrayText

@Composable
fun ResultMainInfoBox(
    request: FlightSearchRequest,
    data: Result,
    onOpenLegsDetail: (String) -> Unit
) {
    Box(
        Modifier
            .padding(20.dp)
            .background(Background)
            .clickable { onOpenLegsDetail(data.id) }) {
        Column(
            Modifier.fillMaxWidth()
        )
        {
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(
                        ComponentBackground,
                        shape = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp)
                    )
                    .padding(15.dp)
            ) {
                Text(
                    text = getDestinationName(data),
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(15.dp))
                DatesBox(start = request.departureDate, end = request.returnDate)
                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = getAdditionalInfoText(request),
                    color = GrayText,
                    fontSize = 13.sp
                )
            }
            Spacer(modifier = Modifier.height(1.dp))
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(ComponentBackground)
                    .padding(15.dp)
            ) {
                Text(
                    text = getLegAirportsText1(data),
                    color = GrayText,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(15.dp))
                LegMainInfoBoxDetail(leg = getLegMainInfo(data.legs.first()))
                if (request.roundTrip) {
                    Spacer(modifier = Modifier.height(15.dp))
                    Divider(modifier = Modifier
                        .height(1.dp)
                        .background(Background))
                    Spacer(modifier = Modifier.height(15.dp))
                    Text(
                        text = getLegAirportsText2(data),
                        color = GrayText,
                        fontSize = 13.sp
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    LegMainInfoBoxDetail(leg = getLegMainInfo(data.legs.last()))
                }
            }
            Spacer(modifier = Modifier.height(1.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .background(
                        ComponentBackground,
                        shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                    )
                    .padding(15.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Ver información",
                    color = CyanBlue,
                    fontWeight = FontWeight.Bold
                )
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = "arrow",
                    tint = CyanBlue
                )
            }

        }
    }

}

fun getLegMainInfo(leg: Leg) = LegMainInfo(
    origin = "", destination = "",
    timeO = getHour(leg.segments.first().departure.localDateTime),
    timeD = getHour(leg.segments.last().arrival.localDateTime),
    airline = Airline(
        leg.segments.first().airline.code,
        leg.segments.first().airline.name,
        leg.segments.first().airline.logoUrl
    ),
    nextDay = nextDay(
        leg.segments.first().departure.localDateTime,
        leg.segments.last().arrival.localDateTime
    ),
    stops = leg.segments.size - 1,
    duration = leg.duration
)

fun getLegAirportsText1(data: Result) = "De ${getOriginName(data)} a ${getDestinationName(data)}"
fun getLegAirportsText2(data: Result) = "De ${getDestinationName(data)} a ${getOriginName(data)}"

fun getRoundTripText(roundTrip: Boolean) = if (roundTrip) "Ida y vuelta" else "Ida"
fun getAdditionalInfoText(request: FlightSearchRequest) =
    "${getPassengersText(request.numPassengers.toInt())} · ${getRoundTripText(request.roundTrip)} · Económico"

fun getDestinationName(data: Result): String =
    data.legs.first().segments.last().arrival.airport.name

fun getOriginName(data: Result): String =
    data.legs.first().segments.first().departure.airport.name


