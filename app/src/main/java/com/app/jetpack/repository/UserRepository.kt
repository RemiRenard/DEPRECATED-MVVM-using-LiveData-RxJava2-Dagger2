package com.app.jetpack.repository

import android.arch.lifecycle.LiveData
import com.app.jetpack.AppExecutors
import com.app.jetpack.data.api.NetworkService
import com.app.jetpack.data.api.apiRequest.ConnectRequest
import com.app.jetpack.data.api.apiResponse.ConnectResponse
import com.app.jetpack.data.model.Resource
import com.app.jetpack.utils.AbsentLiveData
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that handles User objects.
 */
@Singleton
class UserRepository @Inject constructor(
        private val appExecutors: AppExecutors,
        private val networkService: NetworkService
) {

    fun login(connectRequest: ConnectRequest): LiveData<Resource<ConnectResponse>> {
        return object : NetworkBoundResource<ConnectResponse, ConnectResponse>(appExecutors) {

            override fun loadFromDb(): LiveData<ConnectResponse> {
                return AbsentLiveData.create()
            }

            override fun saveCallResult(item: ConnectResponse) {
                // Do nothing
            }

            override fun shouldFetch(data: ConnectResponse?) = data == null

            override fun createCall() = networkService.login(connectRequest)
        }.asLiveData()
    }
}
