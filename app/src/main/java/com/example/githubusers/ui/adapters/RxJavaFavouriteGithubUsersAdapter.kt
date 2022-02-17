package com.example.githubusers.ui.adapters

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.githubusers.model.GithubUser
import javax.inject.Inject

class RxJavaFavouriteGithubUsersAdapter @Inject constructor() : ListAdapter<GithubUser, GithubUserViewHolder>(
    COMPARATOR
) {
    var clickListener: ((GithubUser) -> Unit)? = null

    fun setOnClickListener(clickListener: ((GithubUser) -> Unit)) {
        this.clickListener = clickListener
    }

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

    override fun onBindViewHolder(holder: GithubUserViewHolder, position: Int) {
        getItem(position)?.let {
            val githubUser = it
            holder.bind(githubUser, position)
            holder.itemView.setOnClickListener {
                clickListener?.let { it1 -> it1(githubUser) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUserViewHolder {
        return GithubUserViewHolder.create( parent )
    }
}