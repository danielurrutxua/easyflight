package com.example.easyflight.flights.util.drivers

import org.openqa.selenium.remote.RemoteWebDriver
import org.springframework.stereotype.Component

@Component
abstract class DriverInitializer {

    abstract fun initialize(url: String): RemoteWebDriver
}