package com.app.jetpack.ui.rxExample

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.app.jetpack.R
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class RxExampleActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, RxExampleFragment.newInstance())
                    .commitNow()
        }
    }

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    companion object {

        fun getIntent(context: Context) = Intent(context, RxExampleActivity::class.java)
    }
}
