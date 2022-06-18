package com.example.easyflight.flights.util.drivers.chrome

import com.example.easyflight.flights.util.UserAgentSelector
import com.example.easyflight.flights.util.drivers.DriverInitializer
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.openqa.selenium.remote.DesiredCapabilities
import org.springframework.stereotype.Component

@Component
class ChromeDriverInitializer(private val userAgentSelector: UserAgentSelector): DriverInitializer() {


    override fun initialize(url: String): ChromeDriver {
        System.setProperty("webdriver.chrome.driver", "WebDrivers/chromedriver90.exe")
        val options = ChromeOptions()
        options.addArguments("--disable-blink-features=AutomationControlled")
        // options.setExperimentalOption("excludeSwitches", "enable-automation")
        //options.setExperimentalOption("useAutomationExtension", false)
        options.addArguments("windows-size=1280,800")
        options.addArguments("user-agent=${userAgentSelector.getRandomUserAgent()}")
        val driver = ChromeDriver(options)
        driver.executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})")
        driver.manage().deleteAllCookies()
        driver.get(url)
        return driver
    }

}