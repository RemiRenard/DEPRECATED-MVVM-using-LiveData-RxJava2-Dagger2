package com.app.jetpack.ui.rxExample

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.app.jetpack.repository.RxExampleRepository
import io.reactivex.Observer
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class RxExampleViewModel
@Inject constructor(private val rxExampleRepository: RxExampleRepository) : ViewModel() {

    private val _compositeDisposables = CompositeDisposable()
    private val _ints = MutableLiveData<List<Int>>()
    private val _customError = MutableLiveData<Throwable>()
    private val _jobResult = MutableLiveData<Boolean>()
    private val _stringWithoutSpace = MutableLiveData<List<String>>()

    val ints: MutableLiveData<List<Int>> = _ints
    val strings: MutableLiveData<List<String>> = _stringWithoutSpace
    val customError: MutableLiveData<Throwable> = _customError
    val jobResult: MutableLiveData<Boolean> = _jobResult

    @SuppressLint("CheckResult")
    fun startRxTest() {
        rxExampleRepository.newObservable()
                .subscribe(object : Observer<List<Int>> {
                    override fun onComplete() {
                        // Do nothing for now.
                    }

                    override fun onSubscribe(d: Disposable?) {
                        _compositeDisposables.add(d)
                    }

                    override fun onNext(it: List<Int>?) {
                        _ints.postValue(it)
                    }

                    override fun onError(e: Throwable?) {
                        _customError.postValue(e)
                    }

                })

        // We are using only the onNext because we just want to test the Rx Stream
        // Use an observer instead for an app in production.
        rxExampleRepository.job().subscribe { _jobResult.postValue(it) }
        rxExampleRepository.operationsOnList().subscribe { _stringWithoutSpace.postValue(it) }
    }

    override fun onCleared() {
        // Cancel the API call when the view is destroyed.
        _compositeDisposables.clear()
        super.onCleared()
    }
}
