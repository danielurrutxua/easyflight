package com.example.easyflight.feature_flight.presentation.flights.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.FocusInteraction
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.easyflight.feature_flight.domain.model.Airport
import com.example.easyflight.ui.theme.Background
import com.example.easyflight.ui.theme.GraySoft
import com.example.easyflight.ui.theme.ComponentBackground
import kotlinx.coroutines.flow.filter

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
    val searchResults = remember(mutableText.value) { getSearchResults(mutableText.value) }
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