package com.example.easyflight.feature_flight.presentation.flights.components


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.easyflight.R
import com.example.easyflight.feature_flight.presentation.flights.FlightsEvent
import com.example.easyflight.feature_flight.presentation.flights.FlightsViewModel

@Composable
fun AirportsSelector(viewModel: FlightsViewModel) {

    val airportsRef = ConstrainedLayoutReference("airports")
    val exchangeButtonRef = ConstrainedLayoutReference("exchangeButton")

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
                textFieldState = viewModel.state.value.departureAirport
            ) { value -> viewModel.onEvent(FlightsEvent.TypeDepartureAirport(value)) }
            SimpleTextField(
                label = "Destino",
                cornerSizes = CornerSizes(0, 0, 15, 15),
                drawableId = R.drawable.airplane_landing,
                textFieldState = viewModel.state.value.destinationAirport
            ) { value -> viewModel.onEvent(FlightsEvent.TypeArrivalAirport(value)) }
        }

        ExchangeAirportsButton(
            modifier = Modifier
            .width(45.dp)
            .height(45.dp)
            .constrainAs(exchangeButtonRef) {
                end.linkTo(airportsRef.end, margin = 40.dp)
                top.linkTo(airportsRef.top)
                bottom.linkTo(airportsRef.bottom)

            }, onClick =  { viewModel.onEvent(FlightsEvent.SwapAirports) })

    }
}