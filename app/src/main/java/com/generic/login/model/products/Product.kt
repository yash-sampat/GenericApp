package com.generic.login.model.products

import com.google.gson.annotations.SerializedName

data class Product(
    @SerializedName("previewURL") val previewURL: String,
    @SerializedName("largeImageURL") val largeImageURL: String,
    @SerializedName("user") val user: String,
    @SerializedName("tags") val tags: String,
    @SerializedName("views") val views: Int,
    @SerializedName("likes") val likes: Int,
    @SerializedName("comments") val comments: Int,
)