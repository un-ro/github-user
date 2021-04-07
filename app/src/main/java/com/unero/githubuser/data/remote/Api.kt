package com.unero.githubuser.data.remote

import com.unero.githubuser.BuildConfig.TOKEN
import com.unero.githubuser.data.remote.model.Profile
import com.unero.githubuser.data.remote.model.Result
import com.unero.githubuser.data.remote.model.User
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    @GET("search/users")
    @Headers("Authorization: token $TOKEN")
    suspend fun findUserByUsername(
        @Query("q") username: String
    ): Response<Result>

    @GET("users/{username}")
    @Headers("Authorization: token $TOKEN")
    suspend fun findUserDetail(
        @Path("username") username: String
    ): Response<Profile>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $TOKEN")
    suspend fun findUserFollowers(
        @Path("username") username: String
    ): Response<List<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $TOKEN")
    suspend fun findUserFollowing(
        @Path("username") username: String
    ): Response<List<User>>
}