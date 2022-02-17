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

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class RxJavaGithubUserViewModelTest : TestCase() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var githubUsersDatabase: GithubUsersDatabase
    private lateinit var githubUserDao: GithubUserDao

    @Before
    public override fun setUp() {
        hiltRule.inject()
        githubUsersDatabase = GithubUsersDatabase.getDatabase(ApplicationProvider.getApplicationContext<Context>())
        githubUserDao = githubUsersDatabase.githubUserDao()
    }

    @After
    public override fun tearDown() {

    }

    @Test
    fun insertAndGetGithubUserFromRoomTest() {
        val githubUser = GithubUser(id = 0, name = "Test User", userName = "testuser")
        githubUserDao.insertGithubUser(githubUser).test()
        githubUserDao.getGithubUser(githubUser.id).test().assertValue {
            it.id == githubUser.id
        }
    }

    @Test
    fun deleteGithubUserFromRoomTest() {
        val githubUser = GithubUser(id = 0, name = "Test User", userName = "testuser")
        githubUserDao.insertGithubUser(githubUser).test()
        githubUserDao.deleteGithubUser(githubUser).blockingAwait()
        githubUserDao.getGithubUser(githubUser.id).test().assertValue {
            it.name == null
        }
    }

    @Test
    fun githubUserExistsTest() {
        val githubUser = GithubUser(id = 0, name = "Test User", userName = "testuser")
        githubUserDao.insertGithubUser(githubUser).test()
        githubUserDao.githubUserExists(githubUser.id).test().assertValue { it }
    }

//    @Test
//    fun getGithubUserFromAPI() {
//
//    }
}