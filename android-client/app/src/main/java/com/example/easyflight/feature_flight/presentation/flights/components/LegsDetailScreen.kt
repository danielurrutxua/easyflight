package com.example.easyflight.feature_flight.presentation.flights.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.easyflight.feature_flight.domain.model.service.response.Result
import com.example.easyflight.feature_flight.presentation.flights.components.details.TopBar
import com.example.easyflight.feature_flight.presentation.flights.components.legs.LegDetailInfoBox
import com.example.easyflight.ui.theme.Background

@Composable
fun LegsDetailScreen(result: Result, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopBar(onBack = onBack, text = "", icon = Icons.Default.ArrowBack, color = Background)
        }
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .background(Background)
                .verticalScroll(rememberScrollState())) {
            result.legs.forEach { leg ->
                LegDetailInfoBox(leg)
            }



        }

    }

}


