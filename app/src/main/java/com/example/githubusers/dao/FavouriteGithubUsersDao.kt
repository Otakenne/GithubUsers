package com.example.githubusers.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.githubusers.model.GithubUser
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Data access object to perform bulk operations on the github_user_table (get and delete all users)
 * @author Otakenne
 */
@Dao
interface FavouriteGithubUsersDao {
    /**
     * Delete all persisted github users
     * @return completable indicating success
     * @author Otakenne
     */
    @Query("DELETE FROM github_user_table")
    fun deleteAllGithubUsers(): Completable

    /**
     * Return all persisted github users as an RxJava flowable
     * @return github users as an RxJava flowable
     * @author Otakenne
     */
    @Query("SELECT * FROM github_user_table")
    fun getAllGithubUsers(): Flowable<List<GithubUser>>
}