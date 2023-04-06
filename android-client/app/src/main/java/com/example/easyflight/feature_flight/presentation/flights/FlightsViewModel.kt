package com.example.easyflight.feature_flight.presentation.flights

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.easyflight.feature_flight.domain.model.service.request.FlightSearchRequest
import com.example.easyflight.feature_flight.domain.use_case.FlightUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FlightsViewModel @Inject constructor(
    private val flightUseCases: FlightUseCases
) : ViewModel() {

    private val _state = mutableStateOf(FlightsState())
    val state: State<FlightsState> = _state

    fun onEvent(event: FlightsEvent) {

        when (event) {
            is FlightsEvent.Search -> {
                viewModelScope.launch(Dispatchers.IO) {
                    getFlights()
                }
            }
            is FlightsEvent.TypeDepartureAirport -> {
                _state.value = state.value.copy(
                    departureAirport = event.airport
                )
                /**
                viewModelScope.launch(Dispatchers.IO) {
                flightUseCases.getAirports(state.value.departureAirport)
                .onEach { suggestedAirports ->
                _state.value = state.value.copy(
                suggestedAirports = suggestedAirports
                )
                }
                }
                 **/
            }
            is FlightsEvent.TypeArrivalAirport -> {
                _state.value = state.value.copy(
                    destinationAirport = event.airport
                )
//                viewModelScope.launch(Dispatchers.IO) {
//                    flightUseCases.getAirports(state.value.destinationAirport)
//                        .onEach { suggestedAirports ->
//                            _state.value = state.value.copy(
//                                suggestedAirports = suggestedAirports
//                            )
//                        }
//                }
            }
            is FlightsEvent.SelectFlight -> {
                flightUseCases.openFlightUrl
            }
            FlightsEvent.SwapAirports -> {
                _state.value = state.value.copy(
                    departureAirport = state.value.destinationAirport,
                    destinationAirport = state.value.departureAirport
                )

            }

            is FlightsEvent.SelectDepartureAirport -> {
                _state.value = state.value.copy(
                    departureAirport = state.value.destinationAirport,
                    destinationAirport = state.value.departureAirport
                )
            }
            FlightsEvent.DismissBottomSheet -> {
                _state.value = state.value.copy(
                    showBottomSheet = false
                )
            }

            FlightsEvent.ShowBottomSheet -> {
                _state.value = state.value.copy(
                    showBottomSheet = true
                )
            }
            is FlightsEvent.UpdatePassengers -> {
                _state.value = state.value.copy(
                    numPassengers = event.total
                )

            }
            is FlightsEvent.SetDepartureDate -> {
                _state.value = state.value.copy(
                    departureDate = event.date
                )
            }
            is FlightsEvent.SetReturnDate -> {
                _state.value = state.value.copy(
                    returnDate = event.date
                )
            }
            is FlightsEvent.SetShowOriginResults -> {
                _state.value = state.value.copy(
                    showOriginResults = event.value
                )

            }
            is FlightsEvent.SetShowDestinationResults -> {
                _state.value = state.value.copy(
                    showDestinationResults = event.value
                )

            }
        }
    }

    private fun getFlights() {
        flightUseCases.getFlights(
            FlightSearchRequest(
                origin = state.value.departureAirport,
                destination = state.value.destinationAirport,
                departureDate = state.value.departureDate,
                returnDate = state.value.returnDate,
                numPassengers = state.value.numPassengers.toString()
            )
        ).onEach { flightsSearch ->
            _state.value = state.value.copy(
                flightsSearch = flightsSearch!!
            )
        }
    }
}