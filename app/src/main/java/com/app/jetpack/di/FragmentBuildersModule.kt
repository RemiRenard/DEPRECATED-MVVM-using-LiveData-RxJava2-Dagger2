package com.app.jetpack.di

import com.app.jetpack.ui.main.MainFragment
import com.app.jetpack.ui.rxExample.RxExampleFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeRxExampleFragment(): RxExampleFragment
}
