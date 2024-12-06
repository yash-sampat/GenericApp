package com.generic.login.model.products


import com.google.gson.annotations.SerializedName

data class DataModelProductStatus(
    @SerializedName("hits") val hits: List<Product>,
    @SerializedName("totalHits") val totalHits: Int,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("error_code") val error_code: String,
    @SerializedName("error") val error: String
)
