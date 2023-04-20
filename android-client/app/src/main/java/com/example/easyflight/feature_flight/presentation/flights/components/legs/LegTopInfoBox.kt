package com.example.easyflight.feature_flight.presentation.flights.components.legs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easyflight.R
import com.example.easyflight.feature_flight.domain.model.service.response.Leg
import com.example.easyflight.feature_flight.presentation.flights.components.details.DatesBox
import com.example.easyflight.feature_flight.presentation.flights.components.result.getStopsText
import java.time.LocalDate
import java.time.LocalDateTime

import java.time.format.DateTimeFormatter




@Composable
fun LegTopInfoBox(leg: Leg){
    Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.Start) {
        Text(
            text = getDestinationName(leg),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Desde ${getOriginName(leg)}",
            color = Color.White,
        )
        Spacer(modifier = Modifier.height(15.dp))
        DatesBox(start =toLocalDate(leg.segments.first().departure.localDateTime) )
        Spacer(modifier = Modifier.height(15.dp))
        Row {
            Icon(painter = painterResource(id = R.drawable.schedule), contentDescription = "schedule icon", tint = Color.White)
            Text(text = " ${leg.duration}  ·  ${getStopsText(leg.segments.size - 1)}", color = Color.White)
        }
    }
}

fun toLocalDate(date: String): LocalDate {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    return LocalDateTime.parse(date, formatter).toLocalDate()
}
