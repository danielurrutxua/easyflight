package com.example.easyflight.feature_flight.presentation.flights.components.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easyflight.feature_flight.domain.model.service.response.Option
import com.example.easyflight.feature_flight.presentation.flights.components.search.utils.SimpleButton
import com.example.easyflight.ui.theme.Background
import com.example.easyflight.ui.theme.ComponentBackground
import com.example.easyflight.ui.theme.GrayText

@Composable
fun OptionsBox(options: List<Option>, onOpenProviders: (String) -> Unit, resultId: String) {
    Box(
        Modifier
            .fillMaxWidth()
            .background(ComponentBackground)
            .padding(15.dp)
    ) {
        Column(Modifier.fillMaxWidth()) {
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.Bottom) {
                Text(
                    text = "Se han encontrado ${options.size} proveedores",
                    color = GrayText,
                    fontSize = 13.sp
                )
                Column(horizontalAlignment = Alignment.End) {
                    Text(
                        text = "Desde",
                        color = GrayText,
                        fontSize = 13.sp
                    )
                    Text(
                        text = "${options[0].price} €",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(15.dp))
            Divider(
                Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Background)
            )
            Spacer(modifier = Modifier.height(30.dp))

            SimpleButton(
                text = "Selecciona un proveedor",
                onClick = { onOpenProviders(resultId) })

        }

    }
}