package com.example.githubusers.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.rxjava2.RxPagingSource
import com.example.githubusers.api.GithubUsersService
import com.example.githubusers.model.GithubUser
import com.example.githubusers.model.GithubUsersResponse
import com.example.githubusers.utility.Constants
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RxJavaGithubUsersPagingSource @Inject constructor(
    private val githubUsersService: GithubUsersService
) : RxPagingSource<Int, GithubUser>() {
    override fun getRefreshKey(state: PagingState<Int, GithubUser>): Int? {
        TODO("Not yet implemented")
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, GithubUser>> {
        val pageIndex = params.key ?: Constants.GITHUB_USERS_STARTING_PAGE_INDEX

        return githubUsersService.getGithubUsers(Constants.GITHUB_QUERY_TERM, pageIndex)
            .subscribeOn(Schedulers.io())
            .map { toLoadResult(it, pageIndex, params.loadSize) }
            .onErrorReturn { LoadResult.Error(it) }
    }

    private fun toLoadResult(data: GithubUsersResponse, position: Int, loadSize: Int): LoadResult<Int, GithubUser> {
        val nextKey =
            if (data.githubUsers.isEmpty()) {
                null
            } else {
                position + (loadSize / Constants.GITHUB_PAGE_SIZE)
            }

        return LoadResult.Page(
            data = data.githubUsers,
            prevKey = if (position == Constants.GITHUB_USERS_STARTING_PAGE_INDEX) null else position - 1,
            nextKey = nextKey
        )
    }
}