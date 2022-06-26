package com.example.easyflight.scraping.drivers

import org.openqa.selenium.remote.RemoteWebDriver
import org.springframework.stereotype.Component

@Component
abstract class DriverInitializer {

    abstract fun initialize(url: String): RemoteWebDriver
}