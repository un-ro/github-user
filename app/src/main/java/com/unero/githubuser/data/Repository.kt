package com.unero.githubuser.data

import android.database.Cursor
import androidx.lifecycle.LiveData
import com.unero.githubuser.data.local.model.Favorite
import com.unero.githubuser.data.remote.model.Profile
import com.unero.githubuser.data.remote.model.Result
import com.unero.githubuser.data.remote.model.User
import com.unero.ungithub.data.utils.APIResponse

interface Repository {
    // Remote
    suspend fun findUserByUsername(username: String): APIResponse<Result>
    suspend fun findUserDetail(username: String): APIResponse<Profile>
    suspend fun findUserFollowers(username: String): APIResponse<List<User>>
    suspend fun findUserFollowing(username: String): APIResponse<List<User>>

    // Local
    fun listFav(): LiveData<List<Favorite>>
    fun searchFavorite(username: String): LiveData<Favorite>
    fun getCursor(): Cursor
    fun getItemWidget(): List<Favorite>
    suspend fun add(item: Favorite)
    suspend fun delete(username: String)
    fun deleteAll()
}