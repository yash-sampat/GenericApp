package com.generic.login.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.generic.login.model.products.DataModelProductStatus
import com.generic.login.model.products.Product
import com.generic.login.paging.ProductPagingDataSource
import com.generic.login.utils.PIXABAY_API_PAGE_SIZE
import com.generic.login.webapi.ImageService
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class PhotoRepository @Inject constructor(private val imageService: ImageService) {

    fun getPhotosPaged(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PIXABAY_API_PAGE_SIZE),
            pagingSourceFactory = {
                ProductPagingDataSource(imageService)
            }
        ).flow
    }

    suspend fun getPhotos(apiKey: String, query: String, type: String, indentJSON: Boolean, page: Int, pageSize: Int, editorsChoice: Boolean): Response<DataModelProductStatus> {
        return imageService.getPhotos(apiKey, query, type, indentJSON, page, pageSize, editorsChoice)
    }
}