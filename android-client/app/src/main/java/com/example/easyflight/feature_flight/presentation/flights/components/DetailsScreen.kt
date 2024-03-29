package com.example.easyflight.feature_flight.presentation.flights.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.easyflight.feature_flight.domain.model.service.request.FlightSearchRequest
import com.example.easyflight.feature_flight.domain.model.service.response.Result
import com.example.easyflight.feature_flight.presentation.flights.components.details.OptionsBox
import com.example.easyflight.feature_flight.presentation.flights.components.details.ResultMainInfoBox
import com.example.easyflight.feature_flight.presentation.flights.components.details.TopBar
import com.example.easyflight.ui.theme.Background

@Composable
fun DetailsScreen(
    request: FlightSearchRequest,
    data: Result,
    onBack: () -> Unit,
    onOpenProviders: (String) -> Unit,
    onOpenLegsDetail: (String) -> Unit
) {
    Scaffold(topBar = {
        TopBar(onBack, "Detalles de tu viaje", Icons.Default.ArrowBack)
    }) { paddingValues ->
        Column(
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(paddingValues)
                .background(Background), verticalArrangement = Arrangement.SpaceBetween
        ) {
            ResultMainInfoBox(
                request = request, data = data,
                onOpenLegsDetail = onOpenLegsDetail
            )
            OptionsBox(
                resultId = data.id,
                options = data.options,
                onOpenProviders = onOpenProviders
            )
        }
    }
}


