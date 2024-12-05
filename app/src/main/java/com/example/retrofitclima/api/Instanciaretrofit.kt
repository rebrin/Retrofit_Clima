package com.example.retrofitclima.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Instanciaretrofit {
    private val BaseURL="https://api.openweathermap.org/data/2.5/"
    val climaAPI: APIClima=getInstance().create(APIClima::class.java)

    private fun getInstance(): Retrofit{
        return Retrofit.Builder()
            .baseUrl(BaseURL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}