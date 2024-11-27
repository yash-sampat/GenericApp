package com.generic.login.webapi

import com.generic.login.model.login.DataModelProductStatus
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ImageService {

    @GET("/api")
    suspend fun getPhotos(
        @Query("key") apiKey: String?,
        @Query("q") searchQuery: String?,
        @Query("image_type") type: String?,
        @Query("pretty") indentJSON: Boolean?,
    ): Response<DataModelProductStatus>
}