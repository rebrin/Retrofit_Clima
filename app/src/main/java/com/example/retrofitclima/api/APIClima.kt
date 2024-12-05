package com.example.retrofitclima.api

import com.example.retrofitclima.models.ClimaModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface APIClima {
    @GET("weather")
    suspend fun getClima(
        @Query("appid") appid: String,
        @Query("q") ciudad: String,
        @Query("units") units: String
    ): Response<ClimaModel>
}