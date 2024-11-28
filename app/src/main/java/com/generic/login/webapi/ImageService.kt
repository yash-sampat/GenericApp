package com.generic.login.webapi

import com.generic.login.model.products.DataModelProductStatus
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
        @Query("per_page") pageSize: Int?,
        @Query("editors_choice") editorsChoice: Boolean?
    ): Response<DataModelProductStatus>
}