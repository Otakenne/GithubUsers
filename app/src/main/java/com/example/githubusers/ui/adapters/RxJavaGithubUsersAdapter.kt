package com.example.githubusers.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.TypedArrayUtils.getString
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.githubusers.R
import com.example.githubusers.databinding.GithubUserRowBinding
import com.example.githubusers.model.GithubUser
import javax.inject.Inject

class RxJavaGithubUsersAdapter @Inject constructor() : PagingDataAdapter<GithubUser, GithubUserViewHolder>(COMPARATOR) {
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

class GithubUserViewHolder(private val binding: GithubUserRowBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(githubUser: GithubUser, position: Int) {
        with(githubUser) {
            binding.githubUserRowAvatar.load(githubUser.avatarURL) {
                crossfade(true)
            }
            binding.githubUserRowUsername.text = "@${githubUser.userName}"
        }
    }

    companion object {
        fun create(parent: ViewGroup): GithubUserViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.github_user_row,  parent,false)
            val binding = GithubUserRowBinding.bind(view)
            return GithubUserViewHolder( binding )
        }
    }
}