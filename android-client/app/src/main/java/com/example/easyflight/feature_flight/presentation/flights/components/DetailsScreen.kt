package com.example.easyflight.feature_flight.presentation.flights.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.easyflight.feature_flight.domain.model.service.request.FlightSearchRequest
import com.example.easyflight.feature_flight.domain.model.service.response.Result
import com.example.easyflight.feature_flight.presentation.flights.components.details.ResultMainInfoBox
import com.example.easyflight.feature_flight.presentation.flights.components.details.TopBar
import com.example.easyflight.ui.theme.Background

@Composable
fun DetailsScreen(request: FlightSearchRequest, data: Result, onBack: () -> Unit) {
    Scaffold(topBar = {
        TopBar(onBack)
    }) { paddingValues ->
        Column(
            Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .background(Background)
        ) {
            ResultMainInfoBox(request = request, data = data)
        }
    }
}