package com.example.easyflight.feature_flight.presentation.flights.components.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.easyflight.feature_flight.presentation.flights.components.result.adapters.LegMainInfo
import com.example.easyflight.ui.theme.GrayText


@Composable
fun LegMainInfoBox(leg: LegMainInfo) {
    Box {
        Row(Modifier.fillMaxWidth()) {
            if (leg.airline.logoUrl.isNotEmpty()) {
                Box(
                    modifier = Modifier
                        .size(25.dp)
                        .background(Color.White,shape = RoundedCornerShape(3.dp))
                ) {
                    AsyncImage(
                        model = leg.airline.logoUrl,
                        contentDescription = "${leg.airline.name} logo",
                        modifier = Modifier.size(25.dp).background(Color.White, shape = RoundedCornerShape(3.dp))
                    )
                }
                Spacer(modifier = Modifier.width(15.dp))
            } else{
                Box(
                    modifier = Modifier
                        .size(25.dp)
                        .background(Color.Transparent,shape = RoundedCornerShape(3.dp))
                )
            }
            Column {
                Row(Modifier.fillMaxWidth()) {
                    HoursText(timeO = leg.timeO, timeD = leg.timeD, nextDay = leg.nextDay)

                    Box(Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            text = getStopsText(leg.stops),
                            color = Color.White
                        )
                    }

                }

                Row {

                    Text(
                        text = getAirportsText(leg.origin, leg.destination, leg.airline.name),
                        color = GrayText,
                        fontSize = 13.sp,
                    )

                    Box(Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterEnd),
                            text = leg.duration,
                            color = GrayText,
                            fontSize = 13.sp
                        )
                    }
                }

            }
        }

    }


}

fun getAirportsText(origin: String, destination: String, airline: String) =
    "$origin-$destination, $airline"

fun getTimesText(timeO: String, timeD: String) = "$timeO - $timeD"

fun getStopsText(stops: Int) =
    if (stops == 0) "Directo" else if (stops == 1) "1 escala" else "$stops escalas"




