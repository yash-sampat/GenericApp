package com.generic.login.model.register

import com.generic.login.response.RegisterResponse
import com.google.gson.annotations.SerializedName

data class DataModelRegisterData(
    @SerializedName("UserID") val userID: Int,
    @SerializedName("OTPinfo") val oTPinfo: List<RegisterResponse>
)
