package com.irnanrf.optikoecapstone

import androidx.lifecycle.ViewModel
import com.irnanrf.optikoecapstone.data.api.FaceRepository
import okhttp3.MultipartBody

class ScanFaceViewModel(private val repository: FaceRepository) : ViewModel() {
    fun uploadFaceScan(file: MultipartBody.Part) = repository.addFaceScan(file)
}