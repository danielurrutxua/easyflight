package com.example.easyflight.feature_flight.presentation.flights.components.result

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.easyflight.feature_flight.domain.model.service.request.FlightSearchRequest
import com.example.easyflight.ui.theme.ComponentBackground
import com.example.easyflight.ui.theme.GrayText
import java.time.format.DateTimeFormatter

@Composable
fun RequestBarInfoBox(request: FlightSearchRequest, onBack: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(ComponentBackground, shape = RoundedCornerShape(8.dp))
            .padding(1.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            IconButton(onClick = onBack, modifier = Modifier.size(44.dp)) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
            }

            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Row(verticalAlignment = Alignment.Bottom) {
                    Text(
                        text = "${request.origin} - ${request.destination}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                    Icon(modifier = Modifier.offset(x = (5).dp).size(20.dp),
                        imageVector = Icons.Filled.Person,
                        contentDescription = "Passenger Icon",
                        tint = Color.White,

                    )
                    Text(
                        text = request.numPassengers,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(start = 4.dp),
                        color = Color.White
                    )
                }


                Text(
                    text = "${request.departureDate.format(DateTimeFormatter.ofPattern("MMM dd"))} - ${request.returnDate.format(DateTimeFormatter.ofPattern("MMM dd"))}",
                    color = GrayText,
                    fontSize = 12.sp
                )
            }

        }
    }
}
