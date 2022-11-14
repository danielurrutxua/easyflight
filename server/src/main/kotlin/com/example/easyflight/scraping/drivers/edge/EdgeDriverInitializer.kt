package com.example.easyflight.scraping.drivers.edge

import com.example.easyflight.scraping.drivers.DriverInitializer
import org.openqa.selenium.edge.EdgeDriver
import org.openqa.selenium.edge.EdgeOptions
import org.springframework.stereotype.Component

@Component
class EdgeDriverInitializer: DriverInitializer() {
    override fun initialize(url: String): EdgeDriver {
        System.setProperty("webdriver.edge.driver", "server/WebDrivers/msedgedriver.exe")
        val options = EdgeOptions()
        val driver = EdgeDriver(options)
        driver.executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})")
        driver.manage().deleteAllCookies()
        driver.get(url)
        return driver
    }
}