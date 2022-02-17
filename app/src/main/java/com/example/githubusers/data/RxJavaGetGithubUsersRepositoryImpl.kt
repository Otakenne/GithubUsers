package com.example.githubusers.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.example.githubusers.model.GithubUser
import com.example.githubusers.utility.Constants
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Repository to act as a single source of truth for paged lagos github users
 * @author Otakenne
 */
class RxJavaGetGithubUsersRepositoryImpl @Inject constructor(
    private val pagingSource: RxJavaGithubUsersPagingSource
    ): RxJavaGetGithubUsersRepository {

    /**
     * Gets paged github users from API
     * @return flowable of paged github users
     * @author Otakenne
     */
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