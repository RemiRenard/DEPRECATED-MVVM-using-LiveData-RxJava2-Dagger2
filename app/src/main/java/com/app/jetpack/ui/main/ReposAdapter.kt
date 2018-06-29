package com.app.jetpack.ui.main

import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.view.LayoutInflater
import android.view.ViewGroup
import com.app.jetpack.R
import com.app.jetpack.data.model.Repos
import com.app.jetpack.databinding.RepoItemBinding
import com.app.jetpack.utils.AppExecutors
import com.app.jetpack.ui.common.DataBoundListAdapter

/**
 * A RecyclerView adapter for [Repos] class.
 */
class ReposAdapter(
        private val dataBindingComponent: DataBindingComponent,
        appExecutors: AppExecutors,
        private val repoClickCallback: ((Repos) -> Unit)?) : DataBoundListAdapter<Repos, RepoItemBinding>(
        appExecutors, diffCallback = object : DiffUtil.ItemCallback<Repos>() {
    override fun areItemsTheSame(oldItem: Repos, newItem: Repos): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Repos, newItem: Repos): Boolean {
        return oldItem.name == newItem.name
                && oldItem.description == newItem.description
    }
}) {

    override fun createBinding(parent: ViewGroup): RepoItemBinding {
        val binding = DataBindingUtil.inflate<RepoItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.repo_item,
                parent,
                false,
                dataBindingComponent
        )
        binding.root.setOnClickListener {
            binding.repo?.let {
                repoClickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: RepoItemBinding, item: Repos) {
        binding.repo = item
    }
}
