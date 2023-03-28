package com.example.easyflight.feature_flight.presentation.flights.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.TextFieldValue
import com.example.easyflight.ui.theme.GraySoft
import com.example.easyflight.ui.theme.ComponentBackground

@Composable
fun SimpleTextField(modifier: Modifier = Modifier, text: String = "", label: String = "", cornerSizes: CornerSizes, enabled: Boolean = true, icon: ImageVector) {
    var mutableText by remember { mutableStateOf(TextFieldValue(text)) }
    var labelColor by remember { mutableStateOf(getLabelColor(text = text)) }
    OutlinedTextField(
        value = mutableText,
        onValueChange = { newText ->
            mutableText = newText
            labelColor = getLabelColor(text = mutableText.text)
        },
        modifier = modifier.fillMaxWidth(),
        label = { Text(text = label) },
        placeholder = { Text(text = "Introduce una ciudad o aeropuerto") },
        leadingIcon = { Icon(imageVector = icon, contentDescription = icon.name) },
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
            unfocusedLabelColor = labelColor,
            focusedLabelColor = Transparent,
            leadingIconColor = GraySoft
        )
    )
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