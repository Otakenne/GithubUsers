package com.example.githubusers.di

import com.example.githubusers.api.GithubUsersService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Network module for Hilt
 * @author Otakenne
 */
@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    /**
     * Shows Hilt how to provide a GithubUsersService interface as a singleton
     * @return githubUsersService interface
     * @author Otakenne
     */
    @Provides
    @Singleton
    fun provideGithubUserService(): GithubUsersService {
        return GithubUsersService.create()
    }
}