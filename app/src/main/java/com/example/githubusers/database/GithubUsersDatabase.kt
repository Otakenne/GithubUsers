package com.example.githubusers.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.githubusers.dao.GithubUserDao
import com.example.githubusers.dao.FavouriteGithubUsersDao
import com.example.githubusers.model.GithubUser

/**
 * Creates the application's database
 */
@Database(entities = [GithubUser::class], version = 2, exportSchema = false)
abstract class GithubUsersDatabase : RoomDatabase() {
    abstract fun githubUsersDao(): FavouriteGithubUsersDao
    abstract fun githubUserDao(): GithubUserDao

    companion object {
        @Volatile
        private var INSTANCE: GithubUsersDatabase? = null
        fun getDatabase(context: Context): GithubUsersDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GithubUsersDatabase::class.java,
                    "github_users_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}