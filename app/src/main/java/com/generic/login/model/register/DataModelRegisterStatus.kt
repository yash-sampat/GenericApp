package com.generic.login.model.register


import com.google.gson.annotations.SerializedName

data class DataModelRegisterStatus(
    @SerializedName("status") val status: Int,
    @SerializedName("data") val data: DataModelRegisterData,
    @SerializedName("message") val message: String,
    @SerializedName("error_code") val errorCode: String,
    @SerializedName("error") val error: String
)
