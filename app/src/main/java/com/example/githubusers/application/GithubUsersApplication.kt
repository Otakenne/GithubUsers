package com.example.githubusers.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Extends the application class to enable Hilt create an application wide dependency graph
 * @author Otakenne
 */
@HiltAndroidApp
class GithubUsersApplication: Application()