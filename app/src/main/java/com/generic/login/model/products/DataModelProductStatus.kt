package com.generic.login.model.login


import com.generic.login.model.products.DataModelProductData
import com.google.gson.annotations.SerializedName

data class DataModelProductStatus(
    @SerializedName("code") val code: Int,
    @SerializedName("data") val data: DataModelProductData,
    @SerializedName("message") val message: String,
    @SerializedName("error_code") val error_code: String,
    @SerializedName("error") val error: String
)
