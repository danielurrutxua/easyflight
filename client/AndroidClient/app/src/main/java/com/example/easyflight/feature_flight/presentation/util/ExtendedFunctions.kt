package com.example.easyflight.feature_flight.presentation.util

import java.time.LocalDate

    fun LocalDate.searchRequestFormat(): String {
        return this.year.toString() + "-" + this.month.value + "-" + this.dayOfMonth
    }
