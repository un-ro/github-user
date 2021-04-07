package com.unero.githubuser.data.repository

import androidx.lifecycle.LiveData
import com.unero.githubuser.data.local.UserDao
import com.unero.githubuser.data.local.UserEntity
import kotlinx.coroutines.flow.Flow

class UserRepository(private val userDao: UserDao) {

    val usersLD: LiveData<List<UserEntity>> = userDao.users()

    suspend fun add(userEnitity: UserEntity) {
        userDao.add(userEnitity)
    }

    suspend fun delete(userEnitity: UserEntity) {
        userDao.delete(userEnitity)
    }

    fun search(query: String): Flow<List<UserEntity>> {
        return userDao.search(query)
    }
}