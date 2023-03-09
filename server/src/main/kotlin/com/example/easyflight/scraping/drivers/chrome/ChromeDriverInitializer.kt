package com.example.easyflight.scraping.drivers.chrome

import com.example.easyflight.scraping.util.UserAgentSelector
import com.example.easyflight.scraping.drivers.DriverInitializer
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.stereotype.Component

@Component
class ChromeDriverInitializer(): DriverInitializer() {


    override fun initialize(url: String): ChromeDriver {
        System.setProperty("webdriver.chrome.driver", "WebDrivers/chromedriver.exe")
        val options = ChromeOptions()
        options.addArguments("--disable-blink-features=AutomationControlled")
        options.addArguments("windows-size=1280,800")
        options.addArguments("user-agent=${UserAgentSelector.getRandom()}")
        val driver = ChromeDriver(options)
        driver.executeScript("Object.defineProperty(navigator, 'webdriver', {get: () => undefined})")
        driver.manage().deleteAllCookies()
        driver.get(url)
        return driver
    }

}