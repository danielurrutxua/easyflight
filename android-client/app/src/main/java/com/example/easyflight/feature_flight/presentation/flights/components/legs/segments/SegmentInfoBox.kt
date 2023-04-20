package com.example.easyflight.feature_flight.presentation.flights.components.legs.segments

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.easyflight.R
import com.example.easyflight.feature_flight.domain.model.service.response.Segment
import com.example.easyflight.feature_flight.presentation.flights.components.getHour
import com.example.easyflight.ui.theme.Background
import com.example.easyflight.ui.theme.ComponentBackground
import com.example.easyflight.ui.theme.GrayText

@Composable
fun SegmentInfoBox(segment: Segment) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(ComponentBackground, shape = RoundedCornerShape(8.dp))
            .padding(10.dp)
    ) {
        AirlineInfo(airline = segment.airline)
        Spacer(modifier = Modifier.height(7.dp))
        Divider(
            Modifier
                .fillMaxWidth()
                .background(Background)
                .width(1.dp))
        Spacer(modifier = Modifier.height(15.dp))
        TimeInfo(segment)
    }

}

@Composable
fun TimeInfo(segment: Segment) {
    Row {
        LineWithCircles()
        Spacer(modifier = Modifier.width(15.dp))
        Column(Modifier.fillMaxWidth()) {
            HourAndAirport(hour = getHour(segment.departure.localDateTime), airport = segment.departure.airport.name)
            SpaceAndDivider()
            DurationRow(segment.duration)
            SpaceAndDivider()
            HourAndAirport(hour = getHour(segment.arrival.localDateTime), airport = segment.arrival.airport.name)
        }
    }
}

@Composable
fun DurationRow(duration: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(modifier = Modifier.size(17.dp), painter = painterResource(id = R.drawable.schedule), contentDescription = "schedule icon", tint = GrayText)
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = duration,
            color = GrayText,
        )
    }
}
@Composable
fun SpaceAndDivider(){
    Spacer(modifier = Modifier.height(15.dp))
    Divider(
        Modifier
            .fillMaxWidth()
            .background(Background)
            .width(1.dp))
    Spacer(modifier = Modifier.height(15.dp))
}

@Composable
fun HourAndAirport(hour: String, airport: String){
    Row {
        Text(
            text = hour,
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = airport,
            color = Color.White,
        )
    }
}

