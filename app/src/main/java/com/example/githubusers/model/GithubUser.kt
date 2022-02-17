package com.example.githubusers.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "github_user_table")
data class GithubUser(
    @PrimaryKey
    @NonNull
    val id: Long,
    val name: String? = null,
    val email: String? = null,
    @field:SerializedName("login") val userName: String,
    @field:SerializedName("avatar_url") val avatarURL: String? = null,
    val url: String? = null,
    val public_repos: Int? = null,
    val followers: Int? = null,
    val following: Int? = null,
    val bio: String? = null,
    val location: String? = null,
    var isFavourite: Boolean = false
)
