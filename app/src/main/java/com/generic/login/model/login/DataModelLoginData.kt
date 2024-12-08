package com.generic.login.model.login

import com.generic.login.model.response.LoginResponse
import com.google.gson.annotations.SerializedName

data class DataModelLoginData(
    @SerializedName("UserID") val userID: Int,
    @SerializedName("OTPInfo") val OTPInfo: List<LoginResponse>
)
