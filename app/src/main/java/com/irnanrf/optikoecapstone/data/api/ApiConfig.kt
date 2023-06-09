package com.irnanrf.optikoecapstone.data.api

import androidx.viewbinding.BuildConfig
import com.irnanrf.optikoecapstone.data.api.ApiService.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiConfig {
    fun getApiService(): ApiService {
        val interceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        val timeoutDuration = 120
        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .connectTimeout(timeoutDuration.toLong(), TimeUnit.SECONDS)
            .readTimeout(timeoutDuration.toLong(), TimeUnit.SECONDS)
            .writeTimeout(timeoutDuration.toLong(), TimeUnit.SECONDS)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}