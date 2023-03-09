package com.example.easyflight.scraping.util

import com.opencsv.CSVParserBuilder
import com.opencsv.CSVReaderBuilder
import org.springframework.core.io.ClassPathResource
import java.io.FileReader


class UserAgentSelector {

    companion object {
        fun getRandom(): String = CSVReaderBuilder(FileReader(ClassPathResource("useragents.csv").file))
                .withCSVParser(CSVParserBuilder().withSeparator(';').build())
                .build().readNext()[(0..49).random()]
    }

}