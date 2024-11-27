package com.generic.login.model.login

import com.generic.login.response.LoginResponseOTPinfo
import com.google.gson.annotations.SerializedName

data class DataModelLoginData(
    @SerializedName("UserID") val userID: Int,
    @SerializedName("OTPinfo") val oTPinfo: List<LoginResponseOTPinfo>
)
