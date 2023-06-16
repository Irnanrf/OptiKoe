package com.irnanrf.optikoecapstone.data.api

import android.content.Context

object FaceInjection {
    fun provideRepository(context: Context): FaceRepository {
        val apiService = ApiConfig.getApiService()
        return FaceRepository.getInstance(apiService)
    }
}