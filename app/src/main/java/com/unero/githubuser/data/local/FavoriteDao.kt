package com.unero.githubuser.data.local

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(favorite: Favorite)

    @Query("SELECT * FROM fav ORDER BY id ASC")
    fun listFav(): LiveData<List<Favorite>>

    @Query("DELETE FROM fav WHERE username = :query")
    suspend fun delete(query: String)

    @Query("SELECT * FROM fav WHERE username LIKE :query")
    fun search(query: String): Flow<List<Favorite>>

    @Query("SELECT * FROM fav WHERE username = :query")
    fun searchOne(query: String): LiveData<Favorite>

    @Query("DELETE FROM fav")
    fun deleteAll()

    @Query("SELECT * FROM fav")
    fun getCursor(): Cursor
}