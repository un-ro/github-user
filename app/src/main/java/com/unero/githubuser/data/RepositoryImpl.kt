package com.unero.githubuser.data

import android.database.Cursor
import androidx.lifecycle.LiveData
import com.unero.githubuser.data.local.LocalDataSource
import com.unero.githubuser.data.local.model.Favorite
import com.unero.githubuser.data.remote.Api
import com.unero.githubuser.data.remote.model.Profile
import com.unero.githubuser.data.remote.model.Result
import com.unero.githubuser.data.remote.model.User
import com.unero.ungithub.data.utils.APIResponse
import com.unero.ungithub.data.utils.ResponseHandler.ifError
import com.unero.ungithub.data.utils.ResponseHandler.ifSuccess

class RepositoryImpl(
    private val remote: Api,
    private val local: LocalDataSource
): Repository {
    override suspend fun findUserByUsername(username: String): APIResponse<Result> {
        return try {
            val response = remote.findUserByUsername(username)
            if (response.isSuccessful) {
                ifSuccess(response)
            } else {
                ifError(response)
            }
        } catch (e: Exception) {
            APIResponse.Error(e)
        }
    }

    override suspend fun findUserDetail(username: String): APIResponse<Profile> {
        return try {
            val response = remote.findUserDetail(username)
            if (response.isSuccessful) {
                ifSuccess(response)
            } else {
                ifError(response)
            }
        } catch (e: Exception) {
            APIResponse.Error(e)
        }
    }

    override suspend fun findUserFollowers(username: String): APIResponse<List<User>> {
        return try {
            val response = remote.findUserFollowers(username)
            if (response.isSuccessful) {
                ifSuccess(response)
            } else {
                ifError(response)
            }
        } catch (e: Exception) {
            APIResponse.Error(e)
        }
    }

    override suspend fun findUserFollowing(username: String): APIResponse<List<User>> {
        return try {
            val response = remote.findUserFollowing(username)
            if (response.isSuccessful) {
                ifSuccess(response)
            } else {
                ifError(response)
            }
        } catch (e: Exception) {
            APIResponse.Error(e)
        }
    }

    override fun listFav(): LiveData<List<Favorite>> = local.listFav()

    override fun searchFavorite(username: String): LiveData<Favorite> = local.searchFavorite(username)

    override fun getCursor(): Cursor = local.getCursor()

    override fun getItemWidget(): List<Favorite> = local.getItemWidget()

    override suspend fun add(item: Favorite) {
        local.add(item)
    }

    override suspend fun delete(username: String) {
        local.delete(username)
    }

    override fun deleteAll() {
        local.deleteAll()
    }
}