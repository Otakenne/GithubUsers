package com.example.githubusers.data

import androidx.paging.PagingData
import com.example.githubusers.model.GithubUser
import io.reactivex.Flowable

interface RxJavaGetGithubUsersRepository {
    fun getGithubUsers(): Flowable<PagingData<GithubUser>>
}