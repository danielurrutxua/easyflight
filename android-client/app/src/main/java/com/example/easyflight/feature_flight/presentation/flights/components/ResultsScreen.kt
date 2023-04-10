package com.example.easyflight.feature_flight.presentation.flights.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.easyflight.feature_flight.domain.model.service.response.Result
import com.example.easyflight.feature_flight.presentation.flights.components.result.FlightMainInfoBox
import com.example.easyflight.feature_flight.presentation.flights.components.result.adapters.FlightMainInfo
import com.example.easyflight.feature_flight.presentation.flights.components.result.adapters.LegMainInfo
import com.example.easyflight.ui.theme.Background
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ResultsScreen(data: Map<String, List<Result>>) {
    val sources = data.keys.toList()
    var selectedTabIndex by remember { mutableStateOf(0) }
    val pagerState = rememberPagerState(sources.size)

    Column(Modifier.background(Background)) {
        TabRow(
            selectedTabIndex = selectedTabIndex,
            backgroundColor = MaterialTheme.colors.surface,
            contentColor = LocalContentColor.current
        ) {
            sources.forEachIndexed { index, source ->
                Tab(
                    text = { Text(source) },
                    selected = selectedTabIndex == index,
                    onClick = {
                        selectedTabIndex = index
                    }
                )
            }
        }

        LaunchedEffect(selectedTabIndex) {
            pagerState.animateScrollToPage(selectedTabIndex)
        }

        HorizontalPager(count = sources.size, state = pagerState) { page ->
            val source = sources[page]
            val flights = data[source] ?: emptyList()

            LazyColumn {
                items(flights) { flight ->
                    FlightMainInfoBox(getFlightMainInfo(flight))
                }
            }
        }
    }
}

fun getFlightMainInfo(result: Result) = FlightMainInfo(
    leg1 = LegMainInfo(
        origin = result.legs[0].segments[0].departure.airport.code,
        destination = result.legs[0].segments.last().arrival.airport.code,
        timeD = getHour(result.legs[0].segments[0].departure.localDateTime),
        timeO = getHour(result.legs[0].segments.last().arrival.localDateTime),
        nextDay = nextDay(
            result.legs[0].segments[0].departure.localDateTime,
            result.legs[0].segments.last().arrival.localDateTime
        ),
        stop = result.legs[0].segments.size > 2
    ),
    leg2 = LegMainInfo(
        origin = result.legs[1].segments[0].departure.airport.code,
        destination = result.legs[1].segments.last().arrival.airport.code,
        timeD = getHour(result.legs[1].segments[0].departure.localDateTime),
        timeO = getHour(result.legs[1].segments.last().arrival.localDateTime),
        nextDay = nextDay(
            result.legs[1].segments[0].departure.localDateTime,
            result.legs[1].segments.last().arrival.localDateTime
        ),
        stop = result.legs[1].segments.size > 2
    ),
    airlines = getAirlines(result),
    price = result.options[0].price,
    morePrices = result.options.size > 1
)

fun getAirlines(result: Result): List<String> {
    return result.legs.flatMap { leg ->
        leg.segments.map { segment ->
            segment.airline.name
        }
    }.distinct()
}

fun getHour(fecha: String): String {
    val formatoEntrada = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")
    val fechaHora = LocalDateTime.parse(fecha, formatoEntrada)

    val formatoSalida = DateTimeFormatter.ofPattern("HH:mm")
    return fechaHora.format(formatoSalida)
}

fun nextDay(fecha1: String, fecha2: String): Boolean {
    val fechaLocal1 = LocalDate.parse(fecha1.substringBefore('T'), DateTimeFormatter.ISO_DATE)
    val fechaLocal2 = LocalDate.parse(fecha2.substringBefore('T'), DateTimeFormatter.ISO_DATE)

    return fechaLocal1 != fechaLocal2
}



