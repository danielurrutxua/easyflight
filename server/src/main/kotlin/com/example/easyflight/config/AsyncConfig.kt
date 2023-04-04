package com.example.easyflight.config

import org.springframework.context.annotation.Configuration
import org.springframework.core.task.AsyncTaskExecutor
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class AsyncConfig : AsyncConfigurer, WebMvcConfigurer {

    override fun getAsyncExecutor(): AsyncTaskExecutor {
        val executor = ThreadPoolTaskExecutor()
        executor.corePoolSize = 2 // ajusta el tamaño del pool según tus necesidades
        executor.maxPoolSize = 10 // ajusta el tamaño máximo del pool según tus necesidades
        executor.setQueueCapacity(500) // ajusta la capacidad de la cola según tus necesidades
        executor.setThreadNamePrefix("Async-")
        executor.initialize()
        return executor
    }

    override fun configureAsyncSupport(configurer: AsyncSupportConfigurer) {
        configurer.setDefaultTimeout(180_000L) // ajusta el tiempo de espera aquí, en milisegundos
        configurer.setTaskExecutor(asyncExecutor)
    }
}