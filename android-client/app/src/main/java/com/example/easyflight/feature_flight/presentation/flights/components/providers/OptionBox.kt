package com.example.easyflight.feature_flight.presentation.flights.components.providers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.easyflight.feature_flight.domain.model.service.response.Option
import com.example.easyflight.ui.theme.ComponentBackground

@Composable
fun OptionBox(option: Option) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(ComponentBackground, shape = RoundedCornerShape(5.dp))
    ) {
        Column(Modifier.fillMaxWidth().padding(15.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = option.agent.name,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = "${option.price} €",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
            Spacer(modifier = Modifier.height(15.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if(option.agent.logoUrl.isNotBlank()) {
                    Box(
                        modifier = Modifier
                            .size(60.dp)
                            .background(Color.White,shape = RoundedCornerShape(8.dp))
                    ) {
                        AsyncImage(
                            model = option.agent.logoUrl,
                            contentDescription = "${option.agent.name} logo",
                            modifier = Modifier.size(60.dp).background(Color.White, shape = RoundedCornerShape(8.dp))
                        )
                    }
                } else Text(text = "")
                
                GoToWebButton(url = option.url)

            }
        }

    }
}