package com.app.jetpack.di

import com.app.jetpack.ui.main.MainActivity
import com.app.jetpack.ui.rxExample.RxExampleActivity

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class MainActivityModule {

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [FragmentBuildersModule::class])
    abstract fun contributeRxExampleActivity(): RxExampleActivity
}
