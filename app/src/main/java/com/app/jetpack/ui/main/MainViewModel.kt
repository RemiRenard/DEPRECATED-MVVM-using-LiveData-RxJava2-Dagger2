package com.app.jetpack.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.app.jetpack.data.api.apiRequest.ConnectRequest
import com.app.jetpack.data.api.apiResponse.ConnectResponse
import com.app.jetpack.repository.UserRepository
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MainViewModel
@Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private val _connectResponse = MutableLiveData<ConnectResponse>()
    private val _connectError = MutableLiveData<Throwable>()
    private val _compositeDisposables = CompositeDisposable()

    val connectResponse: MutableLiveData<ConnectResponse> = _connectResponse
    val connectError: MutableLiveData<Throwable> = _connectError

    fun login(email: String, password: String) {
        userRepository.login(ConnectRequest(email, password, "randomString"))
                .subscribe(object : Observer<ConnectResponse> {
                    override fun onComplete() {
                        // Do nothing for now.
                    }

                    override fun onSubscribe(d: Disposable?) {
                        _compositeDisposables.add(d)
                    }

                    override fun onNext(value: ConnectResponse?) {
                        _connectResponse.postValue(value)
                    }

                    override fun onError(e: Throwable?) {
                        _connectError.postValue(e)
                    }

                })
    }

    override fun onCleared() {
        // Cancel the API call when the view is destroyed.
        _compositeDisposables.clear()
        super.onCleared()
    }
}
