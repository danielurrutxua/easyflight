package com.example.easyflight.feature_flight.presentation.flights.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.easyflight.R
import com.example.easyflight.feature_flight.domain.model.service.request.FlightSearchRequest
import com.example.easyflight.feature_flight.domain.model.service.response.Airline
import com.example.easyflight.feature_flight.domain.model.service.response.Result
import com.example.easyflight.feature_flight.presentation.flights.components.result.FlightMainInfoBox
import com.example.easyflight.feature_flight.presentation.flights.components.result.RequestBarInfoBox
import com.example.easyflight.feature_flight.presentation.flights.components.result.adapters.FlightMainInfo
import com.example.easyflight.feature_flight.presentation.flights.components.result.adapters.LegMainInfo
import com.example.easyflight.ui.theme.Background
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ResultsScreen(
    data: Map<String, List<Result>>,
    onBack: () -> Unit,
    request: FlightSearchRequest,
    navigateToDetails: (Int, Int) -> Unit
) {
    val sources = data.keys.toList()
    var selectedTabIndex by remember { mutableStateOf(0) }
    val pagerState = rememberPagerState(sources.size)

    Scaffold(topBar = {
        TopAppBar(
            elevation = 0.dp,
            title = { Row(
                Modifier.padding(end = 13.dp)
                    .fillMaxWidth()
                    .background(Background) // Establece el mismo color de fondo que la TopAppBar
            ) {
                RequestBarInfoBox(
                    request = request,
                    onBack
                )
            }
             },
            backgroundColor = Background,
            contentColor = LocalContentColor.current
        )
    }) {
        Column(
            Modifier
                .background(Background)
                .padding(it)) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                backgroundColor = Color.Transparent,
                contentColor = LocalContentColor.current,
                indicator = { tabPositions ->
                    TabRowDefaults.Indicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                        height = 1.dp,
                        color = Color.White
                    )
                }
            ) {
                sources.forEachIndexed { index, _ ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = {
                            selectedTabIndex = index
                        }
                    ) {
                        if (index == 0) {
                            val painter = painterResource(R.drawable.kayak_logo)
                            Image(
                                painter = painter,
                                contentDescription = "Kayak Logo",
                                modifier = Modifier.size(256.dp, 64.dp)
                            )
                        } else {
                            val painter = painterResource(R.drawable.skyscanner_logo)
                            Image(
                                painter = painter,
                                contentDescription = "Skyscanner Logo",
                                modifier = Modifier.size(256.dp, 64.dp)
                            )
                        }
                    }
                }
            }

            LaunchedEffect(selectedTabIndex) {
                pagerState.animateScrollToPage(selectedTabIndex)
            }

            HorizontalPager(pageCount = sources.size, state = pagerState, userScrollEnabled = false) { page ->
                val source = sources[page]
                val flights = data[source] ?: emptyList()

                LazyColumn {
                    itemsIndexed(flights) { itemIndex, flight ->
                        FlightMainInfoBox(
                            getFlightMainInfo(flight)
                        ) { navigateToDetails(selectedTabIndex, itemIndex) }
                    }
                }
            }
        }
    }

}

fun getFlightMainInfo(result: Result) = FlightMainInfo(
    leg1 = LegMainInfo(
        origin = result.legs.first().segments[0].departure.airport.code,
        destination = result.legs.first().segments.last().arrival.airport.code,
        timeD = getHour(result.legs.first().segments[0].departure.localDateTime),
        timeO = getHour(result.legs.first().segments.last().arrival.localDateTime),
        duration = result.legs[0].duration,
        airline = Airline(
            result.legs.first().segments[0].airline.code,
            result.legs.first().segments[0].airline.name,
            result.legs.first().segments[0].airline.logoUrl
        ),
        nextDay = nextDay(
            result.legs.first().segments[0].departure.localDateTime,
            result.legs.first().segments.last().arrival.localDateTime
        ),
        stops = result.legs.first().segments.size - 1,
    ),
    leg2 = LegMainInfo(
        origin = result.legs[1].segments[0].departure.airport.code,
        destination = result.legs[1].segments.last().arrival.airport.code,
        timeD = getHour(result.legs[1].segments[0].departure.localDateTime),
        timeO = getHour(result.legs[1].segments.last().arrival.localDateTime),
        duration = result.legs[1].duration,
        airline = Airline(
            result.legs[1].segments[0].airline.code,
            result.legs[1].segments[0].airline.name,
            result.legs[1].segments[0].airline.logoUrl
        ),
        nextDay = nextDay(
            result.legs[1].segments[0].departure.localDateTime,
            result.legs[1].segments.last().arrival.localDateTime
        ),
        stops = result.legs[1].segments.size - 1
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



