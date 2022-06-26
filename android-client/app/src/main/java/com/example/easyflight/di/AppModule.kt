package com.example.easyflight.di

import android.app.Application
import com.example.easyflight.feature_flight.data_source.service.api.FlightApiDataSource
import com.example.easyflight.feature_flight.data_source.service.api.FlightApiService
import com.example.easyflight.feature_flight.domain.repository.FlightRepository
import com.example.easyflight.feature_flight.domain.repository.FlightRepositoryImpl
import com.example.easyflight.feature_flight.domain.use_case.FlightUseCases
import com.example.easyflight.feature_flight.domain.use_case.GetFlights
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(Singleton::class)
object AppModule {

    private const val BASE_URL = ""


    @Provides
    @Singleton
    fun provideFlightRepository(): FlightRepository {
        return FlightRepositoryImpl(
            FlightApiDataSource(
                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(FlightApiService::class.java)
            )
        )

    }

    @Provides
    @Singleton
    fun provideFlightUseCases(repository: FlightRepository): FlightUseCases {
        return FlightUseCases(getFlights = GetFlights(repository))

    }


}