package com.example.githubusers.dao

import androidx.room.*
import com.example.githubusers.model.GithubUser
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Data access object to perform single operations on the github_user_table (insert, get and delete single user)
 * @author Otakenne
 */
@Dao
interface GithubUserDao {

    /**
     * Inserts a new github user to the github_user_table
     * @return completable indicating success
     * @author Otakenne
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGithubUser(githubUser: GithubUser): Completable

    /**
     * Deletes a github user from the github_user_table
     * @return completable indicating success
     * @author Otakenne
     */
    @Delete
    fun deleteGithubUser(githubUser: GithubUser): Completable

    /**
     * Gets a github user from the github_user_table using the id parameter
     * @param id: id of github user to extract
     * @return github user as an RxJava flowable
     * @author Otakenne
     */
    @Query("SELECT * FROM github_user_table WHERE id = :id")
    fun getGithubUser(id: Long): Flowable<GithubUser>

    /**
     * Checks if a github user exists in the github_user_table
     * @param id: id of github user to check
     * @return boolean indicating existence of github user
     * @author Otakenne
     */
    @Query("SELECT EXISTS(SELECT * FROM github_user_table WHERE id = :id)")
    fun githubUserExists(id : Long) : Single<Boolean>
}