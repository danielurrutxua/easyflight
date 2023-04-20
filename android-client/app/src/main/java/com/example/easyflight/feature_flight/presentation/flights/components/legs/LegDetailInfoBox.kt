package com.example.easyflight.feature_flight.presentation.flights.components.legs

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.example.easyflight.feature_flight.domain.model.service.request.FlightSearchRequest
import com.example.easyflight.feature_flight.domain.model.service.response.Leg
import com.example.easyflight.feature_flight.presentation.flights.components.legs.segments.SegmentInfoBox
import com.example.easyflight.ui.theme.GrayText
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Composable
fun LegDetailInfoBox(leg: Leg) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(20.dp), horizontalAlignment = Alignment.Start) {
        LegTopInfoBox(leg = leg)
        Spacer(modifier = Modifier.height(15.dp))
        leg.segments.forEachIndexed { index, segment ->
            SegmentInfoBox(segment)
            if (index < leg.segments.size - 1) { // Verifica que no sea la última iteración
                Row(Modifier.padding(start = 20.dp),verticalAlignment = CenterVertically) {
                    VerticalGreyLine()
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = "Espera de ${getWaitTime(leg, index)}",
                        color = Color.White,
                    )
                }

            } else {
                Spacer(modifier = Modifier.height(15.dp))
            }
        }
    }
}

fun getWaitTime(leg: Leg, index: Int): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")

    // Convertir los strings a objetos LocalDateTime
    val time1 = LocalDateTime.parse(leg.segments[index].arrival.localDateTime, formatter)
    val time2 = LocalDateTime.parse(leg.segments[index+1].departure.localDateTime, formatter)

    // Calcular la diferencia de tiempo
    val duration = Duration.between(time1, time2).abs()

    // Extraer las horas y los minutos
    val hours = duration.toHours()
    val minutes = duration.toMinutes() % 60

    return when {
        hours == 0L && minutes == 0L -> "0 min"
        hours == 0L -> "$minutes min"
        minutes == 0L -> "$hours h"
        else -> "$hours h y $minutes min"
    }


}

@Composable
fun VerticalGreyLine() {
    Canvas(modifier = Modifier
        .height(80.dp)
        .width(1.dp)) {
        val circleRadius = 4.dp.toPx()
        val lineHeight = (size.height / 2) - circleRadius
        val strokeWidth = 1.dp.toPx()

        // Dibujar la primera mitad de la línea
        drawLine(
            start = Offset(x = size.width / 2, y = 0f),
            end = Offset(x = size.width / 2, y = lineHeight),
            color = Color.Gray,
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        // Dibujar la segunda mitad de la línea
        drawLine(
            start = Offset(x = size.width / 2, y = size.height - lineHeight),
            end = Offset(x = size.width / 2, y = size.height),
            color = GrayText,
            strokeWidth = strokeWidth,
            cap = StrokeCap.Round
        )

        // Dibujar el círculo blanco no relleno
        drawCircle(
            color = Color.White,
            radius = circleRadius,
            center = Offset(x = size.width / 2, y = size.height / 2),
            style = androidx.compose.ui.graphics.drawscope.Stroke(width = 2.dp.toPx())
        )
    }
}


fun getDestinationName(leg: Leg): String =
    leg.segments.last().arrival.airport.name

fun getOriginName(leg: Leg): String =
    leg.segments.first().departure.airport.name