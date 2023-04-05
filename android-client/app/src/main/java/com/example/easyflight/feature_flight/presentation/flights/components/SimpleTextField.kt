package com.example.easyflight.feature_flight.presentation.flights.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.easyflight.ui.theme.GraySoft
import com.example.easyflight.ui.theme.ComponentBackground

@Composable
fun SimpleTextField(
    modifier: Modifier = Modifier,
    label: String = "",
    cornerSizes: CornerSizes,
    enabled: Boolean = true,
    drawableId: Int,
    textFieldState: String,
    onTextValueChange: (String) -> Unit
) {
    val mutableText = derivedStateOf { textFieldState }
    val labelColor = derivedStateOf { getLabelColor(text = textFieldState) }
    val searchResults = remember(mutableText.value) { getSearchResults(mutableText.value) }
    var showResults by remember { mutableStateOf(true) }
    OutlinedTextField(
        value = mutableText.value,
        onValueChange = { newText ->
            onTextValueChange(newText)
            showResults = true
        },
        modifier = modifier.fillMaxWidth(),
        label = { Text(text = label) },
        placeholder = { Text(text = "Introduce una ciudad o aeropuerto") },
        leadingIcon = {
            Icon(
                painter = painterResource(drawableId),
                contentDescription = "My Icon",
                tint = Color(android.graphics.Color.parseColor("#9ba8b0"))
            )
        },
        singleLine = true,
        enabled = enabled,
        shape =
        MaterialTheme.shapes.small.copy(
            topStart = cornerSizes.topStart,
            topEnd = cornerSizes.topEnd,
            bottomEnd = cornerSizes.bottomEnd,
            bottomStart = cornerSizes.bottomStart
        ),
        colors = TextFieldDefaults.textFieldColors(
            textColor = White,
            disabledTextColor = Transparent,
            backgroundColor = ComponentBackground,
            focusedIndicatorColor = Transparent,
            unfocusedIndicatorColor = Transparent,
            disabledIndicatorColor = Transparent,
            placeholderColor = GraySoft,
            unfocusedLabelColor = labelColor.value,
            focusedLabelColor = Transparent,
            leadingIconColor = GraySoft
        )
    )
    if (showResults && mutableText.value.isNotBlank()) {
        Box(modifier = Modifier.background(Color.White)) { // Agrega el Box con fondo gris
            LazyColumn(modifier = Modifier.fillMaxWidth()) {
                items(searchResults) { result ->
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        result,
                        color = Color.Black,
                        modifier = Modifier
                            .clickable {
                                onTextValueChange(result)
                                showResults = false
                            }
                            .fillMaxWidth(),
                        style = MaterialTheme.typography.body1
                    )
                    Spacer(modifier = Modifier.height(8.dp)) // Agrega espacio entre los resultados
                    Divider(color = Color.Gray, modifier = Modifier.fillMaxWidth()) // Agrega una línea gris para delimitar cada resultado
                }
            }
        }

    }
}
// Función para obtener los resultados de búsqueda (puedes reemplazarla con tu propia lógica)
fun getSearchResults(query: String): List<String> {
    val possibleResults = listOf("Resultado 1", "Resultado 2", "Resultado 3", "Resultado 4")
    return possibleResults.filter { it.contains(query, ignoreCase = true) }
}

private fun getLabelColor(text: String): Color {
    return if (text.isBlank()) GraySoft
    else Transparent
}

class CornerSizes(ts: Int, te: Int, bs: Int, be: Int) {
    var topStart: CornerSize
    var topEnd: CornerSize
    var bottomStart: CornerSize
    var bottomEnd: CornerSize

    init {
        this.topStart = CornerSize(ts)
        this.topEnd = CornerSize(te)
        this.bottomStart = CornerSize(bs)
        this.bottomEnd = CornerSize(be)
    }
}