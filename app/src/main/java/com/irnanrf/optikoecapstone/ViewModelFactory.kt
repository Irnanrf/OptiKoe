package com.irnanrf.optikoecapstone

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.irnanrf.optikoecapstone.data.api.FaceInjection
import com.irnanrf.optikoecapstone.data.api.FaceRepository

class ViewModelFactory(private val repository: FaceRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ScanFaceViewModel::class.java)) {
            return ScanFaceViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.simpleName)
    }

    companion object {
        @Volatile
        private var instance: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory {
            return instance ?: synchronized(this) {
                instance ?: ViewModelFactory(FaceInjection.provideRepository(context))
            }.also { instance = it }
        }
    }

}