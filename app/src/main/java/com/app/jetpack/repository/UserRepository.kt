package com.app.jetpack.repository

import com.app.jetpack.data.api.NetworkService
import com.app.jetpack.data.api.apiRequest.ConnectRequest
import com.app.jetpack.data.api.apiResponse.ConnectResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that handles User objects.
 */
@Singleton
class UserRepository @Inject constructor(private val networkService: NetworkService) {

    fun login(connectRequest: ConnectRequest): Observable<ConnectResponse> {
        return networkService.login(connectRequest)
                .flatMap {
                    // Do something
                    return@flatMap Observable.just(it)
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
