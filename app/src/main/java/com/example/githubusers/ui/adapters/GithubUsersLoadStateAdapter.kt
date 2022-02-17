package com.example.githubusers.ui.adapters

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class GithubUsersLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<GithubUsersLoadStateViewHolder>(){
    override fun onBindViewHolder(holder: GithubUsersLoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): GithubUsersLoadStateViewHolder {
        return GithubUsersLoadStateViewHolder.create(parent, retry)
    }
}