package com.example.easyflight.feature_flight.domain.util

sealed class FlightOrder(val orderType: OrderType) {
    class Time(orderType: OrderType): FlightOrder(orderType)
    class Price(orderType: OrderType): FlightOrder(orderType)
    class Scales(orderType: OrderType): FlightOrder(orderType)

}
