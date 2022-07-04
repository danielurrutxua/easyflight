package com.example.easyflight.feature_flight.presentation.util

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.Month

fun LocalDate.searchRequestFormat() =
    this.year.toString() + "-" + this.month.value + "-" + this.dayOfMonth


fun LocalDate.dateVisualizerFormat() =
    this.dayOfWeek.getSpanishFormatted().plus(", ").plus(this.dayOfMonth).plus(" ")
        .plus(this.month.getSpanishFormatted())


fun DayOfWeek.getSpanishFormatted() = when (this.value) {
    1 -> "lun"
    2 -> "mar"
    3 -> "mié"
    4 -> "jue"
    5 -> "vie"
    6 -> "sab"
    7 -> "dom"
    else -> {
        ""
    }
}

fun Month.getSpanishFormatted() = when (this.value) {
    1 -> "ene"
    2 -> "feb"
    3 -> "mar"
    4 -> "abr"
    5 -> "may"
    6 -> "jun"
    7 -> "jul"
    8 -> "ago"
    9 -> "sep"
    10 -> "oct"
    11 -> "nov"
    12 -> "dic"
    else -> {
        ""
    }
}

