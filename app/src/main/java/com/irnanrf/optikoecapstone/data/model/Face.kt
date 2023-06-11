package com.irnanrf.optikoecapstone.data.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Face(
    @SerializedName("photoUrl")
    val photoUrl: String?
) : Parcelable
