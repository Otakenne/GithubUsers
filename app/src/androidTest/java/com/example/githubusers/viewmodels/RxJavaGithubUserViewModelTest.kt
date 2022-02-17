package com.example.githubusers.viewmodels

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.githubusers.dao.GithubUserDao
import com.example.githubusers.database.GithubUsersDatabase
import com.example.githubusers.model.GithubUser
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.TestCase
import org.junit.*
import org.junit.runner.RunWith
import javax.inject.Inject

/**
 * Class to test the RxJavaGithubUserViewModel's methods
 * @author Otakenne
 */
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class RxJavaGithubUserViewModelTest : TestCase() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    /**
     * Allows tests to run synchronously
     * @author Otakenne
     */
    @Inject
    lateinit var githubUsersDatabase: GithubUsersDatabase
    private lateinit var githubUserDao: GithubUserDao

    /**
     * Documentation provided by Android
     */
    @Before
    public override fun setUp() {
        hiltRule.inject()
        githubUsersDatabase = GithubUsersDatabase.getDatabase(ApplicationProvider.getApplicationContext<Context>())
        githubUserDao = githubUsersDatabase.githubUserDao()
    }

    /**
     * Tests the insertGithubUserFromRoom and getGithubUserFromRoom methods
     * @author Otakenne
     */
    @Test
    fun insertAndGetGithubUserFromRoomTest() {
        val githubUser = GithubUser(id = 0, name = "Test User", userName = "testuser")
        githubUserDao.insertGithubUser(githubUser).test()
        githubUserDao.getGithubUser(githubUser.id).test().assertValue {
            it.id == githubUser.id
        }
    }

    /**
     * Tests the githubUserExists method
     * @author Otakenne
     */
    @Test
    fun githubUserExistsTest() {
        val githubUser = GithubUser(id = 0, name = "Test User", userName = "testuser")
        githubUserDao.insertGithubUser(githubUser).test()
        githubUserDao.githubUserExists(githubUser.id).test().assertValue { it }
    }
}