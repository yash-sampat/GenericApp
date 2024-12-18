package com.generic.login.model.login


import com.google.gson.annotations.SerializedName

data class DataModelLoginStatus(
    @SerializedName("status") val status: Int,
    @SerializedName("data") val data: DataModelLoginData,
    @SerializedName("message") val message: String,
    @SerializedName("error_code") val errorCode: String,
    @SerializedName("error") val error: String
)
