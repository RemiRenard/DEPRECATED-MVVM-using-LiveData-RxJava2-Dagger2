package com.app.jetpack.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.app.jetpack.data.model.Repos
import com.app.jetpack.repository.GithubRepository
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class MainViewModel
@Inject constructor(private val userRepository: GithubRepository) : ViewModel() {

    private val _repositories = MutableLiveData<List<Repos>>()
    private val _httpError = MutableLiveData<Throwable>()
    private val _compositeDisposables = CompositeDisposable()

    val repositories: MutableLiveData<List<Repos>> = _repositories
    val httpError: MutableLiveData<Throwable> = _httpError
    fun getRepos(user: String) {
        userRepository.getRepos(user)
                .subscribe(object : Observer<List<Repos>> {
                    override fun onComplete() {
                        // Do nothing for now.
                    }

                    override fun onSubscribe(d: Disposable?) {
                        _compositeDisposables.add(d)
                    }

                    override fun onNext(value: List<Repos>?) {
                        _repositories.postValue(value)
                    }

                    override fun onError(e: Throwable?) {
                        _httpError.postValue(e)
                    }

                })
    }

    override fun onCleared() {
        // Cancel the API call when the view is destroyed.
        _compositeDisposables.clear()
        super.onCleared()
    }
}
