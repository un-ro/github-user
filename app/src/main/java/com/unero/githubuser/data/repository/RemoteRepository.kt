package com.unero.githubuser.data.repository

import com.unero.githubuser.data.remote.Client
import com.unero.githubuser.data.remote.model.Profile
import com.unero.githubuser.data.remote.model.Result
import com.unero.githubuser.data.remote.model.User
import retrofit2.Response

object RemoteRepository {

    suspend fun findUser(query: String): Response<Result> {
        return Client.api.findUserByUsername(query)
    }

    suspend fun detailUser(username: String): Response<Profile> {
        return Client.api.findUserDetail(username)
    }

    suspend fun followerUser(username: String): Response<List<User>> {
        return Client.api.findUserFollowers(username)
    }

    suspend fun followingUser(username: String): Response<List<User>> {
        return Client.api.findUserFollowing(username)
    }
}