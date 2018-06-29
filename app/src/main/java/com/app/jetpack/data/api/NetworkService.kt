package com.app.jetpack.data.api

import com.app.jetpack.data.api.apiRequest.ConnectRequest
import com.app.jetpack.data.api.apiResponse.ConnectResponse
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.POST

/**
 * REST API access points
 */
interface NetworkService {

    @POST("user/login")
    fun login(@Body params: ConnectRequest): Observable<ConnectResponse>
}
