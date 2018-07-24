package com.app.jetpack.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.app.jetpack.ui.main.MainViewModel
import com.app.jetpack.ui.rxExample.RxExampleViewModel
import com.app.jetpack.viewmodel.JetpackViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RxExampleViewModel::class)
    abstract fun bindRxExampleViewModel(rxExampleViewModel: RxExampleViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: JetpackViewModelFactory): ViewModelProvider.Factory
}
