package com.app.jetpack.data.api

import android.arch.lifecycle.LiveData
import com.app.jetpack.data.api.apiRequest.ConnectRequest
import com.app.jetpack.data.api.apiResponse.ConnectResponse
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * REST API access points
 */
interface NetworkService {

    @POST("user/login")
    fun login(@Body params: ConnectRequest): LiveData<ApiResponse<ConnectResponse>>
}
