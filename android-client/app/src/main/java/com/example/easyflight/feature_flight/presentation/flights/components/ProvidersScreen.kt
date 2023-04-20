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
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.easyflight.feature_flight.domain.model.service.response.Option
import com.example.easyflight.feature_flight.presentation.flights.components.details.TopBar
import com.example.easyflight.feature_flight.presentation.flights.components.providers.OptionBox
import com.example.easyflight.ui.theme.Background

@Composable
fun ProvidersScreen(options: List<Option>, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopBar(onBack = onBack, text = "Selecciona un proveedor", icon = Icons.Default.Close)
        }
    ) { paddingValues ->
        Column(modifier =Modifier.padding(paddingValues).fillMaxWidth().fillMaxHeight().background(
            Background)){
            Spacer(modifier = Modifier.height(20.dp))
            LazyColumn {
                items(options) { option ->
                    OptionBox(option = option)
                }
            }
        }

    }
}

