package com.generic.login.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.generic.login.app.GenericApp
import com.generic.login.model.register.DataModelRegisterBody
import com.generic.login.model.register.DataModelRegisterStatus
import com.generic.login.repository.MainApiRepository
import com.generic.login.utils.Event
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
class RegisterViewModel @Inject constructor(
    application: Application,
    private val repository: MainApiRepository
) :
    AndroidViewModel(application) {
    private val _registerData = MutableLiveData<Event<Resource<DataModelRegisterStatus>>>()

    val registerData: LiveData<Event<Resource<DataModelRegisterStatus>>> = _registerData

    fun registerUser(dataModelRegisterBody: DataModelRegisterBody) = viewModelScope.launch {
        register(dataModelRegisterBody)
    }

    private suspend fun register(dataModelRegisterBody: DataModelRegisterBody) {
        _registerData.postValue(Event(Resource.Loading()))
        try {
            if (hasInternetConnection<GenericApp>()) {
                val response = repository.getRegistration(dataModelRegisterBody)
                if (response.isSuccessful) {
                    when (response.body()!!.status) {
                        200 -> {
                            val successResponse: DataModelRegisterStatus? = response.body()
                            toast(getApplication(), successResponse!!.message)
                            _registerData.postValue(Event(Resource.Success(response.body()!!)))
                        }
                        401 -> {

                            val errorResponse: DataModelRegisterStatus? = response.body()
                            toast(getApplication(), errorResponse!!.error)

                        }
                        412 -> {

                            val errorResponse: DataModelRegisterStatus? = response.body()
                            toast(getApplication(), errorResponse!!.error)
                        }
                    }

                } else {
                    _registerData.postValue(Event(Resource.Error(response.message())))
                }
            } else {
                _registerData.postValue(Event(Resource.Error("No Internet Connection")))
                toast(getApplication(), "No Internet Connection!")
            }
        } catch (e: HttpException) {
            when (e) {
                is IOException -> {
                    _registerData.postValue(Event(Resource.Error(e.message!!)))
                    toast(getApplication(), "Exception ${e.message}")
                }

            }
        } catch (t: Throwable) {
            when (t) {
                is IOException -> {
                    _registerData.postValue(Event(Resource.Error(t.message!!)))
                    //toast(getApplication(), t.message!!)
                }

            }

        }
    }
}