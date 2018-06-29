package com.app.jetpack.repository

import com.app.jetpack.data.api.NetworkService
import com.app.jetpack.data.model.Repos
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository that handles Repos objects.
 */
@Singleton
class GithubRepository @Inject constructor(private val networkService: NetworkService) {

    fun getRepos(user: String): Observable<List<Repos>> {
        return networkService.getRepos(user)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}
