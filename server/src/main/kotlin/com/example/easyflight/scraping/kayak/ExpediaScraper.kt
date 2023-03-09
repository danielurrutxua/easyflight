package com.example.easyflight.scraping.kayak

import org.jsoup.Jsoup
import org.springframework.stereotype.Service

@Service
class ExpediaScraper {

    fun execute(){
        val url = "https://www.esky.es/flights/select/roundtrip/ap/bcn/ap/alc?departureDate=2023-03-15&returnDate=2023-03-23&pa=1&py=0&pc=0&pi=0&sc=economy"

        val userAgent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.36"
        val response = Jsoup.connect(url).header("User-Agent", userAgent).execute()
        val htnl = response.parse()

    }
}