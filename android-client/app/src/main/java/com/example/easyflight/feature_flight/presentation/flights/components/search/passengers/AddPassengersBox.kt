package com.example.easyflight.feature_flight.presentation.flights.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easyflight.feature_flight.presentation.flights.FlightsEvent
import com.example.easyflight.feature_flight.presentation.flights.FlightsViewModel
import com.example.easyflight.feature_flight.presentation.flights.components.search.utils.SimpleButton
import com.example.easyflight.ui.theme.ComponentBackground
import com.example.easyflight.ui.theme.CyanBlue
import com.example.easyflight.ui.theme.GraySoft

@Composable
fun AddPassengersBox(viewModel: FlightsViewModel) {
    val adults = remember { mutableStateOf(1) }
    val youths = remember { mutableStateOf(0) }
    val children = remember { mutableStateOf(0) }

    val total = adults.value + youths.value + children.value
    Surface(
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = ComponentBackground)
                .padding(PaddingValues(horizontal = 15.dp)),
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Viajeros", color = White, fontSize = 20.sp)
                    TextButton(
                        onClick = { viewModel.onEvent(FlightsEvent.DismissBottomSheet) },
                        colors = ButtonDefaults.textButtonColors(contentColor = CyanBlue)
                    ) {
                        Text("Cancelar")
                    }
                }
                AddPassengerRow(
                    passengerType = "Adultos",
                    ageDescription = "mayores de 18 años",
                    value = adults
                )
                Divider(color = GraySoft)
                AddPassengerRow(value = youths, passengerType = "Jóvenes", ageDescription = "12-17")
                Divider(color = GraySoft)
                AddPassengerRow(value = children, passengerType = "Niños", ageDescription = "2-11")

                SimpleButton(
                    text = "Aplicar",
                    onClick = {
                        viewModel.onEvent(FlightsEvent.DismissBottomSheet)
                        viewModel.onEvent(FlightsEvent.UpdatePassengers(total))
                              },
                    modifier = Modifier.padding(PaddingValues(top = 30.dp, bottom = 5.dp))
                )


            }
        }
    }
}


