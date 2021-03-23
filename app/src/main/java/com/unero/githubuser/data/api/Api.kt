package com.unero.githubuser.data.api

import com.unero.githubuser.BuildConfig
import com.unero.githubuser.data.model.Profile
import com.unero.githubuser.data.model.Result
import com.unero.githubuser.data.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun findUserByUsername(
        @Query("q") username: String
    ): Call<Result>

    @GET("users/{username}")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun findUserDetail(
        @Path("username") username: String
    ): Call<Profile>

    @GET("users/{username}/followers")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun findUserFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token ${BuildConfig.GITHUB_TOKEN}")
    fun findUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>
}