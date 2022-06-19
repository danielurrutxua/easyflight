package com.example.easyflight.scraping.util

import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReaderBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import java.io.FileReader


@Component
class UserAgentSelector {

    @Value("\${file.name.useragents}")
    private lateinit var userAgentsFile: String

    fun getRandomUserAgent(): String = CSVReaderBuilder(FileReader(ClassPathResource(userAgentsFile).file))
            .withCSVParser(CSVParserBuilder().withSeparator(';').build())
            .build().readNext()[(0..49).random()]
}