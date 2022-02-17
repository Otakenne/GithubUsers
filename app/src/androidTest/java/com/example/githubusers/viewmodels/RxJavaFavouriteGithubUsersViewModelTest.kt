package com.example.githubusers.viewmodels

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.githubusers.dao.FavouriteGithubUsersDao
import com.example.githubusers.dao.GithubUserDao
import com.example.githubusers.database.GithubUsersDatabase
import com.example.githubusers.model.GithubUser
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.*

import junit.framework.TestCase

import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class RxJavaFavouriteGithubUsersViewModelTest : TestCase() {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var githubUsersDatabase: GithubUsersDatabase
    private lateinit var favouriteGithubUsersDao: FavouriteGithubUsersDao
    private lateinit var githubUserDao: GithubUserDao

    @Before
    public override fun setUp() {
        hiltRule.inject()
        favouriteGithubUsersDao = githubUsersDatabase.githubUsersDao()
        githubUserDao = githubUsersDatabase.githubUserDao()
    }

    @After
    public override fun tearDown() {
    }

    @Test
    fun deleteAllGithubUsers() {
        for (i in 1..5) {
            githubUserDao.insertGithubUser(GithubUser(id = i.toLong(), name = "Test User", userName = "testuser")).test()
        }
        favouriteGithubUsersDao.deleteAllGithubUsers().test()
        favouriteGithubUsersDao.getAllGithubUsers().test().assertValue { it.isEmpty() }
    }

    @Test
    fun getFavouriteGithubUsers() {
        for (i in 1..5) {
            githubUserDao.insertGithubUser(GithubUser(id = i.toLong(), name = "Test User", userName = "testuser")).test()
        }
        favouriteGithubUsersDao.getAllGithubUsers().test().assertValue { it.size == 5 }
    }
}