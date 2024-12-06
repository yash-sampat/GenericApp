package com.generic.login.model.products

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Product(
    @SerializedName("id") val id: Int,
    @SerializedName("previewURL") val previewURL: String,
    @SerializedName("webformatURL") val webformatURL: String,
    @SerializedName("user") val user: String,
    @SerializedName("tags") val tags: String,
    @SerializedName("views") val views: Int,
    @SerializedName("likes") val likes: Int,
    @SerializedName("comments") val comments: Int,
    @SerializedName("collections") val collections: Int,
    @SerializedName("downloads") val downloads: Int,
    @SerializedName("imageSize") val imageSize: Int,
    @SerializedName("type") val type: String,
): Parcelable