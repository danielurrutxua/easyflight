package com.example.easyflight.feature_flight.presentation.flights.components.search.airports

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import com.example.easyflight.ui.theme.ComponentBackground
import com.example.easyflight.ui.theme.GraySoft

@Composable
fun SimpleTextField(
    modifier: Modifier = Modifier,
    label: String = "",
    cornerSizes: CornerSizes,
    enabled: Boolean = true,
    drawableId: Int,
    textFieldState: String,
    onTextValueChange: (String) -> Unit,
    onSetShowResults: (Boolean) -> Unit
) {
    val mutableText = derivedStateOf { textFieldState }
    val labelColor = derivedStateOf { getLabelColor(text = textFieldState) }
    val focusManager = LocalFocusManager.current
    val interactionSource = remember { MutableInteractionSource() }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = ComponentBackground, shape = MaterialTheme.shapes.small.copy(
                    topStart = cornerSizes.topStart,
                    topEnd = cornerSizes.topEnd,
                    bottomEnd = cornerSizes.bottomEnd,
                    bottomStart = cornerSizes.bottomStart
                )
            )
    ) {


        OutlinedTextField(
            value = mutableText.value,
            onValueChange = { newText ->
                onTextValueChange(newText)
                onSetShowResults(true)
            },
            modifier = modifier
                .fillMaxWidth()
                .onFocusChanged { focusState: FocusState ->
                    if (focusState.isFocused) {
                        onTextValueChange("")
                    }
                },
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            interactionSource = interactionSource,
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
            ),
        )
    }
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