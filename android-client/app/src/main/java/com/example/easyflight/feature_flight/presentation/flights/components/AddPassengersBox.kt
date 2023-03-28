package com.example.easyflight.feature_flight.presentation.flights.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easyflight.feature_flight.presentation.flights.FlightsEvent
import com.example.easyflight.feature_flight.presentation.flights.FlightsViewModel
import com.example.easyflight.ui.theme.CyanBlue

@Composable
fun AddPassengersBox(viewModel: FlightsViewModel) {

    if(viewModel.state.value.showBottomSheet) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = White)
                .clip(
                    shape = MaterialTheme.shapes.small.copy(
                        topStart = CornerSize(20.dp),
                        topEnd = CornerSize(20.dp),
                    )
                )
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
                        onClick = { /*TODO*/ },
                        colors = ButtonDefaults.textButtonColors(contentColor = CyanBlue)
                    ) {
                        Text("Cancelar")
                    }
                }
                AddPassengerRow(
                    passengerType = "Adultos",
                    ageDescription = "mayores de 18 años",
                    defaultNum = 1
                )
                AddPassengerRow(passengerType = "Jóvenes", ageDescription = "12-17")
                AddPassengerRow(passengerType = "Niños", ageDescription = "2-11")

                SimpleButton(
                    text = "Aplicar",
                    onClick = { viewModel.onEvent(FlightsEvent.DismissBottomSheet) },
                    modifier = Modifier.padding(PaddingValues(top = 30.dp, bottom = 5.dp))
                )


            }
        }
    }

}
