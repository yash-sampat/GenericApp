package com.generic.login.repository

import com.generic.login.model.login.DataModelProductStatus
import com.generic.login.webapi.ImageService
import retrofit2.Response
import javax.inject.Inject

class PhotoRepository @Inject constructor(private val imageService: ImageService) {

    suspend fun getPhotos(apiKey: String, query: String, type: String, indentJSON: Boolean): Response<DataModelProductStatus> {
        return imageService.getPhotos(apiKey, query, type, indentJSON)
    }
}