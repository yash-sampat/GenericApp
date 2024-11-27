package com.generic.login.webapi

import com.generic.login.model.login.DataModelLoginBody
import com.generic.login.model.login.DataModelLoginStatus
import com.generic.login.model.register.DataModelRegisterBody
import com.generic.login.model.register.DataModelRegisterStatus
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    //login
    @POST("/api/login")
    suspend fun getLogin(
        @Body dataModelLoginBody: DataModelLoginBody
    ): Response<DataModelLoginStatus>

    //register
    @POST("/api/register")
    suspend fun getRegistration(
        @Body dataModelRegisterBody: DataModelRegisterBody
    ): Response<DataModelRegisterStatus>
}