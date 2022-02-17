package com.example.githubusers.model

import com.google.gson.annotations.SerializedName

data class GithubUsersResponse(
    @field:SerializedName("total_count") val totalCount: Int = 0,
    @field:SerializedName("items") val githubUsers: List<GithubUser> = listOf(),
    val nextPage: Int? = null
)
