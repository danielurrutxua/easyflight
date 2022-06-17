package com.example.easyflight.flights.util

import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.stereotype.Component

@Component
class ChromeDriverInitializer(private val userAgentSelector: UserAgentSelector) {


    fun initialize(url: String): ChromeDriver {
        System.setProperty("webdriver.chrome.driver", "Driver/chromedriver-102.exe")
        val options = ChromeOptions()
        options.addArguments("--disable-blink-features=AutomationControlled")
        // options.setExperimentalOption("excludeSwitches", "enable-automation")
        options.setExperimentalOption("useAutomationExtension", false)
        options.addArguments("user-agent=${userAgentSelector.getRandomUserAgent()}")
        val driver = ChromeDriver(options)
        driver.executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})")
        driver.get(url)
        return driver
    }

}