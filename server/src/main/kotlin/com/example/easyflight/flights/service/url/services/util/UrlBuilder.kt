package com.example.easyflight.flights.service.url.services.util

class UrlBuilder {

    private lateinit var url: String

      fun setBase(baseUrl: String): UrlBuilder {
        this.url = baseUrl
        return this
    }

    fun setParam(param: String, paramValue: Any): UrlBuilder {
        this.url = url.replace("{$param}", paramValue.toString())
        return this
    }

    fun build(): String = url
}
