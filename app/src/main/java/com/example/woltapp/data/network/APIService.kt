package com.example.woltapp.data.network

import Restaurants
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

fun interface APIService {

    @GET("pages/restaurants")
    suspend fun fetchRestaurants(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double) : Restaurants

    companion object {
        private const val BASE_URL = "https://restaurant-api.wolt.com/v1/"

        fun create() : APIService {
            val logger = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BASIC
            }

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(logger).build()

            return Retrofit.Builder().client(okHttpClient)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(APIService::class.java)
        }
    }
}