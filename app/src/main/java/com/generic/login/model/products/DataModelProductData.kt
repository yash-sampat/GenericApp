package com.generic.login.model.products

import com.google.gson.annotations.SerializedName

data class DataModelProductData(
    @SerializedName("hits") val hits: List<Product>
)