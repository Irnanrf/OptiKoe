package com.irnanrf.optikoecapstone.data.api

import com.google.gson.annotations.SerializedName

data class BaseResponse(
    @SerializedName("error")
    val error: Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: String?,
    @SerializedName("data")
    val data: dataItems
)

data class dataItems(

    @field:SerializedName("face_shape")
    val faceshape: String,

    @field:SerializedName("probability")
    val prob: String,
)