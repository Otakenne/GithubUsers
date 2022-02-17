package com.example.githubusers.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.rxjava2.cachedIn
import com.example.githubusers.data.RxJavaGetGithubUsersRepositoryImpl
import com.example.githubusers.model.GithubUser
import io.reactivex.Flowable
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class RxJavaGithubUsersViewModel @Inject constructor(
    private val githubUsersRepository: RxJavaGetGithubUsersRepositoryImpl
    ) : ViewModel() {

    @ExperimentalCoroutinesApi
    fun getGithubUsers(): Flowable<PagingData<GithubUser>> {
        return githubUsersRepository
            .getGithubUsers()
            .cachedIn(viewModelScope)
    }
}