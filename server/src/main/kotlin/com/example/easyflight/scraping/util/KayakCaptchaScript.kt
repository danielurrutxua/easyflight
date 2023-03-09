package com.example.easyflight.scraping.util

import org.springframework.util.ResourceUtils

class KayakCaptchaScript {
    companion object {
        fun execute(url: String) {
            val scriptFile = try {
                ResourceUtils.getFile("classpath:scripts/recaptcha_kayak.py")
            } catch (ex: Exception) {
                // Manejar la excepción aquí
                null
            }

            if (scriptFile != null) {
                val pb = ProcessBuilder("py", scriptFile.absolutePath, url)
                pb.redirectErrorStream(true)
                val process = pb.start()
                process.waitFor()
                // Hacer algo con el proceso si es necesario
            }
        }
    }

}