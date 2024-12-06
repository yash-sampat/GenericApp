package com.generic.login.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.generic.login.model.products.Product
import com.generic.login.utils.PIXABAY_API_KEY
import com.generic.login.utils.PIXABAY_API_PAGE_SIZE
import com.generic.login.webapi.ImageService
import retrofit2.HttpException
import java.io.IOException

class ProductPagingDataSource(
    private val imageService: ImageService
) : PagingSource<Int, Product>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val nextPageNumber = params.key ?: 1
            val response = imageService.getPhotos(
                PIXABAY_API_KEY,
                "sports shoes", "photo", true, nextPageNumber, PIXABAY_API_PAGE_SIZE, false
            )
            val responseData = mutableListOf<Product>()
            val data = response.body()?.hits ?: emptyList()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (nextPageNumber > 1) nextPageNumber - 1 else null,
                nextKey = if (nextPageNumber <= (response.body()?.totalHits?.div(
                        PIXABAY_API_PAGE_SIZE
                    ))!!
                ) nextPageNumber + 1 else null
            )
        } catch (exception: IOException) {
            val error = IOException("Please Check Internet Connection")
            LoadResult.Error(error)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return null
    }
}