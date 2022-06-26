package com.example.easyflight.feature_flight.domain.util

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}
