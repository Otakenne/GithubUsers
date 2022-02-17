package com.example.githubusers.ui.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.githubusers.model.GithubUser
import javax.inject.Inject

/**
 * Paging adapter for the github users fragment
 * @author Otakenne
 */
class RxJavaGithubUsersAdapter @Inject constructor() : PagingDataAdapter<GithubUser, GithubUserViewHolder>(COMPARATOR) {
    /**
     * Click listener set by adapter's caller
     * @author Otakenne
     */
    private var clickListener: ((GithubUser) -> Unit)? = null

    /**
     * Listens for click events to each item view
     * @param clickListener: function that accepts a GithubUser instance and executes a command defined by adapter's caller
     * @author Otakenne
     */
    fun setOnClickListener(clickListener: ((GithubUser) -> Unit)) {
        this.clickListener = clickListener
    }

    /**
     * Compares changes in the paging adapter and reacts by updating the view
     * @author Otakenne
     */
    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<GithubUser>() {
            override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
                return oldItem == newItem
            }
        }
    }

    /**
     * Documentation provided by Android
     */
    override fun onBindViewHolder(holder: GithubUserViewHolder, position: Int) {
        getItem(position)?.let {
            val githubUser = it
            holder.bind(githubUser)
            holder.itemView.setOnClickListener {
                clickListener?.let { it1 -> it1(githubUser) }
            }
        }
    }

    /**
     * Documentation provided by Android
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUserViewHolder {
        return GithubUserViewHolder.create( parent )
    }
}