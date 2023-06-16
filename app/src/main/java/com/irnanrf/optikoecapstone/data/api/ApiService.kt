package com.irnanrf.optikoecapstone.data.api

import okhttp3.MultipartBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    companion object {
        const val BASE_URL = "https://api-dot-dev-optikoe.et.r.appspot.com/"
    }

    @Multipart
    @POST("face")
    suspend fun addFaceScan(
        @Part file: MultipartBody.Part,
    ): BaseResponse
}