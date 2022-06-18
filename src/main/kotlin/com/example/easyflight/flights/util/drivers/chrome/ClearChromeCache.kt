package com.example.easyflight.flights.util.drivers.chrome

import org.openqa.selenium.By
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.stereotype.Component

@Component
class ClearChromeCache {

    @Throws(InterruptedException::class)
    fun clearCache() {
        System.setProperty("webdriver.chrome.driver", "WebDrivers/chromedriver90.exe")
        val chromeOptions = ChromeOptions()
        chromeOptions.addArguments("disable-infobars")
        chromeOptions.addArguments("start-maximized")
        val driver = ChromeDriver(chromeOptions)
        driver.get("chrome://settings/clearBrowserData")
        Thread.sleep(5000)
        driver.switchTo().activeElement()
        driver.findElement(By.cssSelector("* /deep/ #clearBrowsingDataConfirm")).click()
        Thread.sleep(5000)
    }


}