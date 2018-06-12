package com.app.jetpack.ui.main

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import com.app.jetpack.data.api.apiRequest.ConnectRequest
import com.app.jetpack.data.api.apiResponse.ConnectResponse
import com.app.jetpack.data.model.Resource
import com.app.jetpack.repository.UserRepository
import com.app.jetpack.utils.AbsentLiveData
import javax.inject.Inject

class MainViewModel
@Inject constructor(userRepository: UserRepository) : ViewModel() {

    private val _connectRequest = MutableLiveData<ConnectRequest>()

    val login: LiveData<Resource<ConnectResponse>> = Transformations
            .switchMap(_connectRequest) { connectResponse ->
                if (connectResponse == null) {
                    AbsentLiveData.create()
                } else {
                    userRepository.login(_connectRequest.value!!)
                }
            }

    fun setLogin(email: String?, password: String?) {
        if (_connectRequest.value != ConnectRequest(email!!, password!!, "AAA")) {
            _connectRequest.value = ConnectRequest(email, password, "AAA")
        }
    }

    fun retry() {
        _connectRequest.value?.let {
            _connectRequest.value = it
        }
    }
}
