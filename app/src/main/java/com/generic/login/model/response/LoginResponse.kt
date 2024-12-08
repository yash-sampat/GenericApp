package com.generic.login.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class LoginResponse(
    @SerializedName("ID") val id: Int,
    @SerializedName("TransactionMode") val transactionMode: String,
    @SerializedName("OTPValidTill") val OTPValidTill: String,
    @SerializedName("OTP") val OTP: String
) : Serializable
