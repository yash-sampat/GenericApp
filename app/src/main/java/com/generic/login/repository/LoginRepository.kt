package com.generic.login.repository

import com.generic.login.model.login.DataModelLoginBody
import com.generic.login.model.login.DataModelLoginStatus
import com.generic.login.webapi.ApiService
import retrofit2.Response
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getLogin(dataModelLoginBody: DataModelLoginBody): Response<DataModelLoginStatus> {
        return apiService.getLogin(dataModelLoginBody)
    }
}