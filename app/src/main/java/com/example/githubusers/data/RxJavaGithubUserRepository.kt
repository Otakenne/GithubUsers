package com.example.githubusers.data

import com.example.githubusers.api.GithubUsersService
import com.example.githubusers.dao.GithubUserDao
import com.example.githubusers.model.GithubUser
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class RxJavaGithubUserRepository @Inject constructor(
    private val githubUserDao: GithubUserDao,
    private val githubUsersService: GithubUsersService
) {
    fun insertGithubUserToRoom(githubUser: GithubUser): Completable {
        return githubUserDao.insertGithubUser(githubUser)
    }

    fun deleteGithubUserFromRoom(githubUser: GithubUser) : Completable {
        return githubUserDao.deleteGithubUser(githubUser)
    }

    fun githubUserExists(id: Long): Single<Boolean> {
        return githubUserDao.githubUserExists(id)
    }

    fun getGithubUserFromRoom(id: Long): Flowable<GithubUser> {
        return githubUserDao.getGithubUser(id)
    }

    fun getGithubUserFromAPI(UUID: String): Single<GithubUser> {
        return githubUsersService.getGithubUser(UUID)
    }
}