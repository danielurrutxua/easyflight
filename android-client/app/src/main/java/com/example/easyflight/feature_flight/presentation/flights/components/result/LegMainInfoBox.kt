package com.example.easyflight.feature_flight.presentation.flights.components.result

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easyflight.feature_flight.presentation.flights.components.result.adapters.LegMainInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


@Composable
fun LegMainInfoBox(leg: LegMainInfo) {
    Box {
        Column{
            Row {
                // Primera fila, primera columna
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.White)) {
                            append(leg.timeO)
                        }
                    }
                )

                // Primera fila, segunda columna
                if (leg.stop) {
                    Row(modifier = Modifier.align(Alignment.CenterVertically)) {
                        Spacer(
                            modifier = Modifier
                                .width(10.dp) // Agregado para limitar el ancho del Spacer
                                .height(1.dp)
                                .background(Color.Gray)
                        )
                        Surface(
                            modifier = Modifier
                                .width(10.dp)
                                .height(2.dp),
                            shape = RoundedCornerShape(4.dp),
                            color = Color.Gray,
                            border = BorderStroke(1.dp, Color.Gray)
                        ) {}
                        Spacer(
                            modifier = Modifier
                                .width(10.dp) // Agregado para limitar el ancho del Spacer
                                .height(1.dp)
                                .background(Color.Gray)
                        )
                    }
                } else {
                    Divider(
                        modifier = Modifier
                            .width(10.dp) // Agregado para limitar el ancho del Divider
                            .height(1.dp)
                            .background(Color.Gray)
                    )
                }
                // Primera fila, tercera columna
                Text(
                    buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.White)) {
                            append(leg.timeD)
                        }
                        if (leg.nextDay) {
                            withStyle(style = SpanStyle(fontSize = 12.sp, color = Color.Gray)) {
                                append(" +1")
                            }
                        }
                    }
                )

            }
            Row {

                // Segunda fila, primera columna
                Text(
                    text = leg.origin,
                    color = Color.White
                )


                // Segunda fila, segunda columna
                Text(
                    text = timeDifference(leg.timeO, leg.timeD, leg.nextDay),
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.offset(y = (-4).dp)
                )


                // Segunda fila, tercera columna
                Text(
                    text = leg.destination,
                    color = Color.White
                )
            }
        }


    }
}

fun timeDifference(time1: String, time2: String, nextDay: Boolean): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val firstTime = LocalDateTime.parse("2000-01-01 $time1", formatter)
    var secondTime = LocalDateTime.parse("2000-01-01 $time2", formatter)

    if (nextDay) {
        secondTime = secondTime.plusDays(1)
    }

    val duration = ChronoUnit.MINUTES.between(firstTime, secondTime)
    val hours = duration / 60
    val minutes = duration % 60

    return "%d h %d m".format(hours, minutes)
}


