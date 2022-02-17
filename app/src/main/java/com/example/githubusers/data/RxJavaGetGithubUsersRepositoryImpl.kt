package com.example.githubusers.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.example.githubusers.model.GithubUser
import com.example.githubusers.utility.Constants
import io.reactivex.Flowable
import javax.inject.Inject

class RxJavaGetGithubUsersRepositoryImpl @Inject constructor(
    private val pagingSource: RxJavaGithubUsersPagingSource
    ): RxJavaGetGithubUsersRepository {

    override fun getGithubUsers(): Flowable<PagingData<GithubUser>> {
        return Pager(
            config = PagingConfig(
                pageSize = Constants.GITHUB_PAGE_SIZE,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { pagingSource }
        ).flowable
    }
}