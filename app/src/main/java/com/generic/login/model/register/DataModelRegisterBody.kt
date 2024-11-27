package com.generic.login.model.register

import com.google.gson.annotations.SerializedName

data class DataModelRegisterBody(
    @SerializedName("EmailORMobile") val emailORMobile : String,
    @SerializedName("Password") val password : String,
    @SerializedName("Age") val age : String,
    @SerializedName("TransactionType") val transactionType : String,
    @SerializedName("DeviceID") val deviceID : String
)
