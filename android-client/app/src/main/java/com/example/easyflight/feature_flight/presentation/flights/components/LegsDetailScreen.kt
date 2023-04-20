package com.example.easyflight.feature_flight.presentation.flights.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.easyflight.feature_flight.domain.model.service.request.FlightSearchRequest
import com.example.easyflight.feature_flight.domain.model.service.response.Result
import com.example.easyflight.feature_flight.presentation.flights.components.details.TopBar
import com.example.easyflight.feature_flight.presentation.flights.components.legs.LegDetailInfoBox
import com.example.easyflight.feature_flight.presentation.flights.components.providers.OptionBox
import com.example.easyflight.ui.theme.Background

@Composable
fun LegsDetailScreen(request: FlightSearchRequest,result: Result, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopBar(onBack = onBack, text = "", icon = Icons.Default.ArrowBack, color = Background)
        }
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .background(Background)) {
            result.legs.forEach { leg ->
                LegDetailInfoBox(request, leg)
                Spacer(modifier = Modifier.height(30.dp))
            }



        }

    }

}


