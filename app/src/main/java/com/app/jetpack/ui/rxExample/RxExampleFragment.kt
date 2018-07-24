package com.app.jetpack.ui.rxExample

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.app.jetpack.R
import com.app.jetpack.databinding.RxExampleFragmentBinding
import com.app.jetpack.di.Injectable
import com.app.jetpack.ui.binding.FragmentDataBindingComponent
import com.app.jetpack.utils.autoCleared
import javax.inject.Inject


class RxExampleFragment : Fragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    companion object {
        fun newInstance() = RxExampleFragment()
    }

    var binding by autoCleared<RxExampleFragmentBinding>()
    var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private lateinit var viewModel: RxExampleViewModel

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<RxExampleFragmentBinding>(
                inflater,
                R.layout.rx_example_fragment,
                container,
                false,
                dataBindingComponent
        )
        binding = dataBinding
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(RxExampleViewModel::class.java)
        observeViewModel()
        viewModel.startRxTest()
    }

    private fun observeViewModel() {
        viewModel.customError.observe(this, Observer {
            Toast.makeText(context, it?.message, Toast.LENGTH_SHORT).show()
        })
        viewModel.ints.observe(this, Observer {
            it?.map {
                Log.i("item list ", it.toString())
            }
        })
        viewModel.jobResult.observe(this, Observer {
            Log.i("Job result ", it.toString())
        })
    }
}
