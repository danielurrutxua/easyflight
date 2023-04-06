package com.example.easyflight.feature_flight.presentation.flights.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.easyflight.feature_flight.domain.model.Airport
import com.example.easyflight.ui.theme.Background

@Composable
fun AirportSuggestionList(
    searchText: String,
    onTextValueChange: (String) -> Unit,
    onSetShowResults: (Boolean) -> Unit
) {
    val searchResults = remember(searchText) { getSearchResults(searchText) }
    val focusManager = LocalFocusManager.current
    Box(modifier = Modifier.background(Background)) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(searchResults) { result ->
                Row(
                    modifier = Modifier
                        .clickable {
                            onTextValueChange(formatAirportText(result))
                            onSetShowResults(false)
                            focusManager.clearFocus()
                        }
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            highlightMatchingText(result.name, searchText),
                            color = Color.White,
                            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
                        )
                        Text(
                            highlightMatchingText(result.country, searchText),
                            color = Color.White,
                            style = MaterialTheme.typography.caption
                        )
                    }
                    Text(
                        highlightMatchingText(result.iata, searchText),
                        color = Color.White,
                        style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
            }
        }
    }
}

@Composable
fun highlightMatchingText(text: String, query: String): AnnotatedString {
    val matchingRange = text.indexOf(query, ignoreCase = true)

    return buildAnnotatedString {
        if (matchingRange >= 0) {
            append(text.substring(0, matchingRange))
            pushStyle(SpanStyle(color = Color.Cyan))
            append(text.substring(matchingRange, matchingRange + query.length))
            pop()
            append(text.substring(matchingRange + query.length))
        } else {
            append(text)
        }
    }
}

// Función para obtener los resultados de búsqueda (puedes reemplazarla con tu propia lógica)
fun getSearchResults(query: String): List<Airport> {
    val possibleResults = listOf(
        Airport("JFK", "John F. Kennedy International Airport", "United States"),
        Airport("LAX", "Los Angeles International Airport", "United States"),
        Airport("LHR", "Heathrow Airport", "United Kingdom"),
        Airport("CDG", "Charles de Gaulle Airport", "France")
    )
    val filteredResults = possibleResults.filter {
        it.iata.contains(query, ignoreCase = true) ||
                it.name.contains(query, ignoreCase = true) ||
                it.name.contains(query, ignoreCase = true)
    }

    return filteredResults.sortedWith(compareBy(
        { !it.iata.startsWith(query, ignoreCase = true) },
        { !it.name.startsWith(query, ignoreCase = true) },
        { !it.country.startsWith(query, ignoreCase = true) },
        { it.iata },
        { it.name },
        { it.country }
    ))
}

fun formatAirportText(airport: Airport): String {
    val truncatedName = if (airport.name.length > 30) {
        "${airport.name.substring(0, 30)}..."
    } else {
        airport.name
    }
    return "$truncatedName (${airport.iata})"
}