package com.example.githubusers.di

import android.content.Context
import com.example.githubusers.dao.FavouriteGithubUsersDao
import com.example.githubusers.dao.GithubUserDao
import com.example.githubusers.database.GithubUsersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Database module for Hilt
 * @author Otakenne
 */
@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    /**
     * Shows Hilt how to provide a GithubUserDao interface
     * @param githubUsersDatabase: an instance of the application's database
     * @return githubUserDao interface
     * @author Otakenne
     */
    @Provides
    fun provideGithubUserDao(githubUsersDatabase: GithubUsersDatabase): GithubUserDao {
        return githubUsersDatabase.githubUserDao()
    }

    /**
     * Shows Hilt how to provide a FavouriteGithubUsersDao interface
     * @param githubUsersDatabase: an instance of the application's database
     * @return favouriteGithubUsersDao interface
     * @author Otakenne
     */
    @Provides
    fun provideFavouriteGithubUsersDao(githubUsersDatabase: GithubUsersDatabase): FavouriteGithubUsersDao {
        return githubUsersDatabase.githubUsersDao()
    }

    /**
     * Shows Hilt how to provide the application's database instance as a singleton object
     * @param context: application context provided by Hilt
     * @return application's database instance
     * @author Otakenne
     */
    @Provides
    @Singleton
    fun providesDatabase(@ApplicationContext context: Context): GithubUsersDatabase {
        return GithubUsersDatabase.getDatabase(context = context)
    }
}