package com.irnanrf.optikoecapstone.data.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import okhttp3.MultipartBody
import java.lang.Exception

class FaceRepository(private val apiService: ApiService) {

    fun addFaceScan(
        file: MultipartBody.Part
    ): LiveData<ResponseState<BaseResponse>> = liveData {
        emit(ResponseState.Loading)
        try {
            val response = apiService.addFaceScan(file)
            emit(ResponseState.Success(response))
        } catch (e: Exception) {
            Log.d("FaceScan", e.message.toString())
            emit(ResponseState.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: FaceRepository? = null
        fun getInstance(
            apiService: ApiService
        ): FaceRepository =
            instance ?: synchronized(this) {
                instance ?: FaceRepository(apiService)
            }.also { instance = it }
    }
}