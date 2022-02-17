package com.example.githubusers.data

import com.example.githubusers.dao.FavouriteGithubUsersDao
import com.example.githubusers.model.GithubUser
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Repository to act as a single source of truth for favourite github users
 * @author Otakenne
 */
class RxJavaFavoriteGithubUsersRepository @Inject constructor(
    private val favouriteGithubUsersDao: FavouriteGithubUsersDao
) {

    /**
     * Deletes all github users from the github_users_table
     * @return completable indicating success
     * @author Otakenne
     */
    fun deleteAllGithubUsers(): Completable {
        return favouriteGithubUsersDao.deleteAllGithubUsers()
    }

    /**
     * Get all github users from the github_users_table
     * @return github users as an RxJava flowable
     * @author Otakenne
     */
    fun getAllGithubUsers(): Flowable<List<GithubUser>> {
        return favouriteGithubUsersDao.getAllGithubUsers()
    }
}