package com.example.easyflight.scraping.util

import org.springframework.stereotype.Component

@Component
class UrlBuilder {

    private lateinit var url: String

     fun setBaseUrl(baseUrl: String): UrlBuilder {
        this.url = baseUrl
        return this
    }

    fun setParamIntoUrl(param: String, paramValue: Any): UrlBuilder {
        this.url = url.replace("{$param}", paramValue.toString())
        return this
    }

    fun build(): String = url
}
