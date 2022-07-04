package com.example.easyflight.feature_flight.presentation.flights.components

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.easyflight.feature_flight.presentation.util.dateVisualizerFormat
import com.example.easyflight.ui.theme.GraySoft
import com.example.easyflight.ui.theme.ComponentBackground
import java.time.LocalDate
import java.util.*

@Composable
fun DatesVisualizer() {

    val mContext = LocalContext.current

    // Declaring integer values
    // for year, month and day
    val year: Int
    val month: Int
    val day: Int

    // Initializing a Calendar
    val mCalendar = Calendar.getInstance()

    // Fetching current year, month and day
    year = mCalendar.get(Calendar.YEAR)
    month = mCalendar.get(Calendar.MONTH)
    day = mCalendar.get(Calendar.DAY_OF_MONTH)

    mCalendar.time = Date()

    // Declaring a string value to
    // store date in string format
    val departureDate = remember { mutableStateOf(LocalDate.now()) }
    val returnDate = remember { mutableStateOf(LocalDate.now().plusDays(4)) }

    // Declaring DatePickerDialog and setting
    // initial values as current values (present year, month and day)
    val depDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            departureDate.value = LocalDate.of(mYear, mMonth, mDayOfMonth)
        }, year, month, day
    )
    val retDatePickerDialog = DatePickerDialog(
        mContext,
        { _: DatePicker, mYear: Int, mMonth: Int, mDayOfMonth: Int ->
            returnDate.value = LocalDate.of(mYear, mMonth, mDayOfMonth)
        }, year, month, day
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(59.dp)
            .clip(
                shape = RoundedCornerShape(7.dp)
            )
            .padding(top = 20.dp)
            .background(color = ComponentBackground),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier.padding(PaddingValues(start = 12.dp)),
            imageVector = Icons.Default.DateRange,
            contentDescription = "Date range",
            tint = GraySoft
        )
        TextButton(
            modifier = Modifier.padding(PaddingValues(start = 8.dp)),
            onClick = {
                depDatePickerDialog.show()
            }, colors = ButtonDefaults.textButtonColors(contentColor = White)
        ) {
            Text(departureDate.value.dateVisualizerFormat())

        }
        Text(" — ")
        TextButton(
            onClick = {
                retDatePickerDialog.show()
            },
            colors = ButtonDefaults.textButtonColors(contentColor = White)
        ) {
            Text(returnDate.value.dateVisualizerFormat())
        }
    }
}




