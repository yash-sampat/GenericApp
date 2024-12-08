package com.generic.login.model.register

import com.generic.login.model.response.RegisterResponse
import com.google.gson.annotations.SerializedName

data class DataModelRegisterData(
    @SerializedName("UserID") val userID: Int,
    @SerializedName("OTPInfo") val OTPInfo: List<RegisterResponse>
)
