package com.example.easyflight.di

import com.example.easyflight.feature_flight.data_source.service.api.FlightApiDataSource
import com.example.easyflight.feature_flight.data_source.service.api.FlightApiService
import com.example.easyflight.feature_flight.domain.repository.FlightRepository
import com.example.easyflight.feature_flight.domain.repository.FlightRepositoryImpl
import com.example.easyflight.feature_flight.domain.use_case.FlightUseCases
import com.example.easyflight.feature_flight.domain.use_case.GetAirports
import com.example.easyflight.feature_flight.domain.use_case.GetFlights
import com.example.easyflight.feature_flight.domain.use_case.OpenFlightUrl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    private const val BASE_URL = "http://10.0.2.2:8080/"


    @Provides
    @Singleton
    fun provideFlightRepository(): FlightRepository {
        return FlightRepositoryImpl(
            FlightApiDataSource(
                Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(
                        OkHttpClient.Builder()
                            .connectTimeout(100, TimeUnit.SECONDS)
                            .readTimeout(100, TimeUnit.SECONDS).build()
                    )
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(FlightApiService::class.java)
            )
        )

    }

    @Provides
    @Singleton
    fun provideFlightUseCases(repository: FlightRepository): FlightUseCases {
        return FlightUseCases(
            getFlights = GetFlights(repository),
            getAirports = GetAirports(repository),
            openFlightUrl = OpenFlightUrl(),

            )

    }


}