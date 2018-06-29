package com.app.jetpack.ui.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.app.jetpack.R
import com.app.jetpack.databinding.MainFragmentBinding
import com.app.jetpack.di.Injectable
import com.app.jetpack.ui.binding.FragmentDataBindingComponent
import com.app.jetpack.utils.AppExecutors
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

    private var adapter by autoCleared<ReposAdapter>()
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
        binding = dataBinding
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory)
                .get(MainViewModel::class.java)
        initRecyclerView()
        observeViewModel()
        button_find_repos.setOnClickListener {
            if (username.text.isNotBlank()) {
                viewModel.getRepos(username.text.toString())
            } else {
                Toast.makeText(context, R.string.empty_text_warning, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initRecyclerView() {
        val adapter = ReposAdapter(dataBindingComponent, appExecutors) {
            Log.i("ReposAdapter", "ITEM CLICKED : id : $id")
        }
        this.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(context)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter

    }

    private fun observeViewModel() {
        viewModel.repositories.observe(this, Observer {
            binding.repositories = it
            adapter.submitList(it)
            // this is only necessary because espresso cannot read data binding callbacks.
            binding.executePendingBindings()
        })
        viewModel.httpError.observe(this, Observer {
            adapter.submitList(emptyList())
            Toast.makeText(context, it?.message, Toast.LENGTH_SHORT).show()
        })
    }
}
