package com.example.easyflight.feature_flight.presentation.flights.components.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.easyflight.feature_flight.presentation.flights.FlightsEvent
import com.example.easyflight.feature_flight.presentation.flights.FlightsViewModel
import com.example.easyflight.feature_flight.presentation.flights.components.AddPassengersBox
import com.example.easyflight.feature_flight.presentation.flights.components.DatesVisualizer
import com.example.easyflight.feature_flight.presentation.flights.components.PassengersButton
import com.example.easyflight.feature_flight.presentation.flights.components.search.utils.SimpleButton
import com.example.easyflight.feature_flight.presentation.flights.components.search.airports.AirportsSelector
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchContainer(viewModel: FlightsViewModel, roundTrip: Boolean) {

    val coroutineScope = rememberCoroutineScope()
    val bottomSheetVisible = viewModel.state.value.showBottomSheet
    val bottomSheetState =
        rememberModalBottomSheetState(initialValue = if (bottomSheetVisible) ModalBottomSheetValue.Expanded else ModalBottomSheetValue.Hidden)

    LaunchedEffect(bottomSheetVisible) {
        if (bottomSheetVisible) {
            coroutineScope.launch { bottomSheetState.show() }
        } else {
            coroutineScope.launch { bottomSheetState.hide() }
        }
    }

    // Detectar cambios en el estado del BottomSheet
    LaunchedEffect(bottomSheetState.isVisible) {
        if (!bottomSheetState.isVisible) {
            viewModel.onEvent(FlightsEvent.DismissBottomSheet)
        }
    }

    ModalBottomSheetLayout(
        sheetState = bottomSheetState,
        sheetContent = { AddPassengersBox(viewModel = viewModel) },
        sheetBackgroundColor = Color.Transparent,
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        20.dp
                    ), verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    AirportsSelector(viewModel)
                    DatesVisualizer(
                        start = viewModel.state.value.departureDate,
                        end = viewModel.state.value.returnDate,
                        onSetStartDate = { date ->
                            viewModel.onEvent(
                                FlightsEvent.SetDepartureDate(
                                    date
                                )
                            )
                        },
                        onSetFinalDate = { date -> viewModel.onEvent(FlightsEvent.SetReturnDate(date)) },
                        roundTrip = roundTrip,
                        showDepartureDateError = viewModel.state.value.showDepartureDateError,
                        showReturnDateError = viewModel.state.value.showReturnDateError
                    )

                    PassengersButton(
                        value = viewModel.state.value.numPassengers,
                        onClick = { viewModel.onEvent(FlightsEvent.ShowBottomSheet) })

                }

                SimpleButton(
                    text = "Buscar vuelos",
                    onClick = { viewModel.onEvent(FlightsEvent.Search(roundTrip)) })
            }
        })

}




