package com.example.easyflight.feature_flight.presentation.flights.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.easyflight.feature_flight.domain.model.Flight
import com.example.easyflight.ui.theme.Background
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ResultsScreen(data: Map<String, List<Flight>>) {
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
                    Text(text = "Vuelo: ${flight.name}")
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewFlightScreen() {
    val flights1 = listOf(Flight(1, "Vuelo 1A"), Flight(2, "Vuelo 1B"))
    val flights2 = listOf(Flight(3, "Vuelo 2A"), Flight(4, "Vuelo 2B"))
    val data = mapOf("Fuente1" to flights1, "Fuente2" to flights2)

    ResultsScreen(data = data)
}