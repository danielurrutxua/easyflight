package com.example.easyflight.feature_flight.presentation.flights.components.search.airports


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.easyflight.R
import com.example.easyflight.feature_flight.presentation.flights.FlightsEvent
import com.example.easyflight.feature_flight.presentation.flights.FlightsViewModel
import com.example.easyflight.feature_flight.presentation.flights.components.AirportSuggestionList
import com.example.easyflight.feature_flight.presentation.flights.components.ErrorComponent
import com.example.easyflight.feature_flight.presentation.flights.components.ExchangeAirportsButton

@Composable
fun AirportsSelector(viewModel: FlightsViewModel) {

    val airportsRef = ConstrainedLayoutReference("airports")
    val exchangeButtonRef = ConstrainedLayoutReference("exchangeButton")
    val departureAirportText = viewModel.state.value.departureAirport
    val destinationAirportText = viewModel.state.value.destinationAirport
    Column(modifier = Modifier.fillMaxWidth()) {
        ConstraintLayout(
            modifier = Modifier.fillMaxWidth()
        ) {

            Column(modifier = Modifier
                .constrainAs(airportsRef) {
                    top.linkTo(parent.top)
                }) {
                SimpleTextField(
                    label = "Origen",
                    cornerSizes = CornerSizes(15, 15, 0, 0),
                    drawableId = R.drawable.airplane_takeoff,
                    textFieldState = departureAirportText,
                    onTextValueChange = { value ->
                        viewModel.onEvent(
                            FlightsEvent.TypeDepartureAirport(
                                value
                            )
                        )
                    },
                    onSetShowResults = { value ->
                        viewModel.onEvent(
                            FlightsEvent.SetShowOriginResults(
                                value
                            )
                        )
                    }
                )
                Divider(color = Color.Transparent, thickness = 1.dp)
                SimpleTextField(
                    label = "Destino",
                    cornerSizes = CornerSizes(0, 0, 15, 15),
                    drawableId = R.drawable.airplane_landing,
                    textFieldState = destinationAirportText,
                    onTextValueChange = { value ->
                        viewModel.onEvent(FlightsEvent.TypeArrivalAirport(value))
                    },
                    onSetShowResults = { value ->
                        viewModel.onEvent(FlightsEvent.SetShowDestinationResults(value))
                    }
                )
            }

            ExchangeAirportsButton(
                modifier = Modifier
                    .width(45.dp)
                    .height(45.dp)
                    .constrainAs(exchangeButtonRef) {
                        end.linkTo(airportsRef.end, margin = 40.dp)
                        top.linkTo(airportsRef.top)
                        bottom.linkTo(airportsRef.bottom)

                    }, onClick = { viewModel.onEvent(FlightsEvent.SwapAirports) })

        }
        if(viewModel.state.value.showOriginEmptyError) {
            ErrorComponent(errorMessage = "Elige un aeropuerto origen")
        }
        if(viewModel.state.value.showDestinationEmptyError) {
            ErrorComponent(errorMessage = "Elige un aeropuerto destino")
        }
        if (viewModel.state.value.showOriginResults &&
            departureAirportText.isNotBlank()
        ) {
            AirportSuggestionList(
                searchText = departureAirportText,
                suggestedAirports = viewModel.state.value.suggestedAirports,
                onTextValueChange = { value ->
                    viewModel.onEvent(FlightsEvent.TypeDepartureAirport(value))
                },
                onSetShowResults = { value ->
                    viewModel.onEvent(FlightsEvent.SetShowOriginResults(value))
                }
            )
        }

        if (viewModel.state.value.showDestinationResults &&
            destinationAirportText.isNotBlank()
        ) {
            AirportSuggestionList(
                searchText = destinationAirportText,
                onTextValueChange = { value ->
                    viewModel.onEvent(FlightsEvent.TypeArrivalAirport(value))
                },
                onSetShowResults = { value ->
                    viewModel.onEvent(FlightsEvent.SetShowDestinationResults(value))
                },
                suggestedAirports = viewModel.state.value.suggestedAirports
            )
        }

    }
}