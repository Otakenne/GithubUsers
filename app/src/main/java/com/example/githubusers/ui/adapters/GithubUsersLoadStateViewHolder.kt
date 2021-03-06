package com.example.githubusers.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.example.githubusers.R
import com.example.githubusers.databinding.GithubUsersFooterBinding

/**
 * Sets the view for the paged adapter's footer (the loading animation, error message textView and the button that triggers a reload of the subsequent adapter items)
 * @author Otakenne
 */
class GithubUsersLoadStateViewHolder(
    private val binding: GithubUsersFooterBinding,
    retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.retryButton.setOnClickListener { retry.invoke() }
    }

    /**
     * Set the visibility of each view depending on the load state
     * @author Otakenne
     */
    fun bind(loadState: LoadState) {
        if (loadState is LoadState.Error) {
            binding.errorMsg.text = loadState.error.localizedMessage
        }
        binding.progressBar.isVisible = loadState is LoadState.Loading
        binding.retryButton.isVisible = loadState is LoadState.Error
        binding.errorMsg.isVisible = loadState is LoadState.Error
    }

    companion object {
        fun create(parent: ViewGroup, retry: () -> Unit): GithubUsersLoadStateViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.github_users_footer, parent, false)
            val binding = GithubUsersFooterBinding.bind(view)
            return GithubUsersLoadStateViewHolder(binding, retry)
        }
    }
}