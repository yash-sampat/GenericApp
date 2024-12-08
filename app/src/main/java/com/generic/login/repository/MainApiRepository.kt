package com.generic.login.repository

import com.generic.login.model.login.DataModelLoginBody
import com.generic.login.model.login.DataModelLoginStatus
import com.generic.login.model.register.DataModelRegisterBody
import com.generic.login.model.register.DataModelRegisterStatus
import com.generic.login.webapi.ApiService
import retrofit2.Response
import javax.inject.Inject

class MainApiRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun login(dataModelLoginBody: DataModelLoginBody): Response<DataModelLoginStatus> {
        return apiService.login(dataModelLoginBody)
    }

    suspend fun register(dataModelRegisterBody: DataModelRegisterBody): Response<DataModelRegisterStatus> {
        return apiService.register(dataModelRegisterBody)
    }
}