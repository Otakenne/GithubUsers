package com.example.githubusers.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.githubusers.R
import com.example.githubusers.databinding.GithubUserRowBinding
import com.example.githubusers.model.GithubUser

/**
 * View holder to set the view for each paged item
 * @author Otakenne
 */
class GithubUserViewHolder(private val binding: GithubUserRowBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(githubUser: GithubUser) {
        binding.githubUserRowAvatar.load(githubUser.avatarURL) {
            crossfade(true)
        }
        binding.githubUserRowUsername.text = "@${githubUser.userName}"
    }

    companion object {
        fun create(parent: ViewGroup): GithubUserViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.github_user_row,  parent,false)
            val binding = GithubUserRowBinding.bind(view)
            return GithubUserViewHolder( binding )
        }
    }
}