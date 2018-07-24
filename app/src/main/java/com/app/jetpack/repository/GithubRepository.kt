package com.app.jetpack.repository

import com.app.jetpack.data.api.NetworkService
import com.app.jetpack.data.db.RepoDao
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
class GithubRepository @Inject constructor(private val networkService: NetworkService,
                                           private val repoDao: RepoDao) {

    private val repositories: MutableList<Repos> = arrayListOf()

    /**
     * Get the list of repository from the database first and when repositories from the network are fetched,
     * update the local database.
     *
     * @param user String
     * @return Observable<List<Repos>>
     */
    fun getRepos(user: String): Observable<List<Repos>> {
        return networkService.getRepos(user)
                .onErrorReturn { repositories }
                .flatMap { repoDao.insertRepos(it);Observable.just(it) }
                .publish { Observable.merge(it, getReposFromDb(user).takeUntil(it)) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    /**
     * Get repositories from the local database.
     *
     * @param user String
     * @return Observable<List<Repos>>
     */
    private fun getReposFromDb(user: String): Observable<List<Repos>> {
        return repoDao.load(user)
                .map {
                    repositories.clear()
                    repositories.addAll(it)
                    return@map it
                }
                .toObservable()
    }
}

