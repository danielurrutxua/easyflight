package com.example.easyflight.scraping.util

import java.util.*

fun <T> Optional<T>.safeGet(): T? {
    return if (this.isPresent) {
        get()
    } else {
        null
    }
}