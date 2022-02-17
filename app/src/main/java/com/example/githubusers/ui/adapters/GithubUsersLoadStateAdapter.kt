package com.example.githubusers.ui.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

/**
 * Manages adapter loadState and retry functionality
 * @author Otakenne
 */
class GithubUsersLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<GithubUsersLoadStateViewHolder>(){
    override fun onBindViewHolder(holder: GithubUsersLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): GithubUsersLoadStateViewHolder {
        return GithubUsersLoadStateViewHolder.create(parent, retry)
    }
}