package com.app.jetpack.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.jetpack.AppExecutors
import com.app.jetpack.R
import com.app.jetpack.databinding.MainFragmentBinding
import com.app.jetpack.di.Injectable
import com.app.jetpack.ui.binding.FragmentDataBindingComponent
import com.app.jetpack.utils.RetryCallback
import com.app.jetpack.utils.autoCleared
import kotlinx.android.synthetic.main.main_fragment.*
import javax.inject.Inject


class MainFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    @Inject
    lateinit var appExecutors: AppExecutors

    companion object {
        fun newInstance() = MainFragment()
    }

    var binding by autoCleared<MainFragmentBinding>()
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<MainFragmentBinding>(
                inflater,
                R.layout.main_fragment,
                container,
                false,
                dataBindingComponent
        )
        dataBinding.retryCallback = object : RetryCallback {
            override fun retry() {
                viewModel.retry()
            }
        }
        binding = dataBinding
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MainViewModel::class.java)
        button_login.setOnClickListener {
            viewModel.setLogin(username.text.toString(), password.text.toString())
            viewModel.login.observe(this, Observer {
                binding.user = it?.data?.user
                binding.connectResponse = it?.data
                // this is only necessary because espresso cannot read data binding callbacks.
                binding.executePendingBindings()
            })
        }
    }
}
