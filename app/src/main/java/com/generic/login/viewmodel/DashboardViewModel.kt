package com.generic.login.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.generic.login.app.LoginApp
import com.generic.login.model.login.DataModelProductStatus
import com.generic.login.repository.PhotoRepository
import com.generic.login.utils.Event
import com.generic.login.utils.PIXABAY_API_KEY
import com.generic.login.utils.Resource
import com.generic.login.utils.hasInternetConnection
import com.generic.login.utils.toast
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class DashboardViewModel @Inject constructor(application: Application,
                                             private val repository: PhotoRepository
) :
    AndroidViewModel(application) {
    private val _productData = MutableLiveData<Event<Resource<DataModelProductStatus>>>()

    val productData: LiveData<Event<Resource<DataModelProductStatus>>> = _productData

    fun getProducts() = viewModelScope.launch {
        getPhotos()
    }

    suspend fun getPhotos() {
        _productData.postValue(Event(Resource.Loading()))
        try {
            if (hasInternetConnection<LoginApp>()) {
                val response = repository.getPhotos(PIXABAY_API_KEY,
                    "sports+shoes", "photo", true)
                if (response.isSuccessful) {
                    if (response.body()!!.code == 200) {
                        val successresponse: DataModelProductStatus? = response.body()
                        toast(getApplication(), successresponse!!.message)
                        _productData.postValue(Event(Resource.Success(response.body()!!)))
                    } else if (response.body()!!.code == 401) {

                        val errorresponse: DataModelProductStatus? = response.body()
                        toast(getApplication(), errorresponse!!.error)

                    } else if (response.body()!!.code == 412) {

                        val errorresponse: DataModelProductStatus? = response.body()
                        toast(getApplication(), errorresponse!!.error)
                    }

                } else {
                    _productData.postValue(Event(Resource.Error(response.message())))
                }
            } else {
                _productData.postValue(Event(Resource.Error("No Internet Connection")))
                toast(getApplication(), "No Internet Connection.!")
            }
        } catch (e: HttpException) {
            when (e) {
                is IOException -> {
                    _productData.postValue(Event(Resource.Error(e.message!!)))
                    toast(getApplication(), "Exception ${e.message}")
                }

            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    _productData.postValue(Event(Resource.Error(t.message!!)))
                    toast(getApplication(), t.message!!)
                }

            }

        }
    }
}