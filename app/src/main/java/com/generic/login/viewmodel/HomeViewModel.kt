package com.generic.login.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.generic.login.model.products.Product
import com.generic.login.repository.PhotoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class HomeViewModel @Inject constructor(application: Application,
                                        private val repository: PhotoRepository
) :
    AndroidViewModel(application) {
    fun getPhotosPaged(): Flow<PagingData<Product>> {
        return repository.getPhotosPaged().cachedIn(viewModelScope)
    }
}