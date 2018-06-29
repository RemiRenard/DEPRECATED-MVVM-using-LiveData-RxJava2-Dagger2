package com.app.jetpack.data.api

import com.app.jetpack.data.model.Repos
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * REST API access points
 */
interface NetworkService {

    @GET("users/{user}/repos")
    fun getRepos(@Path("user") user: String): Observable<List<Repos>>
}
