package com.generic.login.repository

import com.generic.login.model.products.DataModelProductStatus
import com.generic.login.webapi.ImageService
import retrofit2.Response
import javax.inject.Inject

class PhotoRepository @Inject constructor(private val imageService: ImageService) {

    suspend fun getPhotos(apiKey: String, query: String, type: String, indentJSON: Boolean, pageSize: Int, editorsChoice: Boolean): Response<DataModelProductStatus> {
        return imageService.getPhotos(apiKey, query, type, indentJSON, pageSize, editorsChoice)
    }
}