package com.generic.login.repository

import com.generic.login.model.register.DataModelRegisterBody
import com.generic.login.model.register.DataModelRegisterStatus
import com.generic.login.webapi.ApiService
import retrofit2.Response
import javax.inject.Inject

class RegisterRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getRegistration(dataModelLoginBody: DataModelRegisterBody): Response<DataModelRegisterStatus> {
        return apiService.getRegistration(dataModelLoginBody)
    }
}