package com.unero.githubuser.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(userEnitity: UserEntity)

    @Query("SELECT * FROM user ORDER BY id ASC")
    fun users(): LiveData<List<UserEntity>>

    @Delete
    suspend fun delete(userEnitity: UserEntity)

    @Query("SELECT * FROM user WHERE username LIKE :query")
    fun search(query: String): Flow<List<UserEntity>>
}