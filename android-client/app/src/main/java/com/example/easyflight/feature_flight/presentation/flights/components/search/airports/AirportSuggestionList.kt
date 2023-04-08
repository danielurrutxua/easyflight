package com.example.easyflight.feature_flight.presentation.flights.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
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
    onSetShowResults: (Boolean) -> Unit,
    suggestedAirports: List<Airport>
) {
    val focusManager = LocalFocusManager.current
    val scrollState = rememberLazyListState()

    Box(modifier = Modifier.background(Background)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .scrollable(orientation = Orientation.Vertical, state = scrollState),
            state = scrollState
        ) {
            items(suggestedAirports) { result ->
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

fun formatAirportText(airport: Airport): String {
    val displayText = if (airport.name.length < 20) {
        "${airport.name}, ${airport.country}"
    } else {
        airport.name
    }

    val truncatedText = displayText.take(30) + if (displayText.length > 30) "..." else ""

    return "$truncatedText (${airport.iata})"
}