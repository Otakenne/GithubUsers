package com.example.githubusers.api

import com.example.githubusers.model.GithubUser
import com.example.githubusers.model.GithubUsersResponse
import com.example.githubusers.utility.Constants
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

/**
 * Interface responsible for calls to the Github API
 * @author Otakenne
 */
interface GithubUsersService {
    /**
     * Returns all the users related to search term (lagos) from the GitHub backend
     * @param q: search term (lagos)
     * @param page: integer to control pagination
     * @return RxJava single shot of the GithubUserResponse model
     * @author Otakenne
     */
    @GET("search/users")
    fun getGithubUsers(
        @Query("q") q: String,
        @Query("page") page: Int
    ): Single<GithubUsersResponse>

    /**
     * Returns the information related to a single user specified by the {UUID} path parameter
     * @param UUID: unique user identification of a github user
     * @author Otakenne
     */
    @GET("users/{UUID}")
    fun getGithubUser(
        @Path("UUID") UUID: String
    ): Single<GithubUser>

    /**
     * Creates a retrofit instance
     * @author Otakenne
     */
    companion object {
        fun create(): GithubUsersService {
            return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constants.BASE_URL)
                .build()
                .create(GithubUsersService::class.java)
        }
    }
}