package com.unero.githubuser.data.local.room

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.unero.githubuser.data.local.model.Favorite
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {

    // App
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(favorite: Favorite)

    @Query("SELECT * FROM fav ORDER BY id ASC")
    fun listFav(): LiveData<List<Favorite>>

    @Query("DELETE FROM fav WHERE username = :query")
    suspend fun delete(query: String)

    @Query("SELECT * FROM fav WHERE username LIKE :query")
    fun search(query: String): Flow<List<Favorite>>

    @Query("SELECT * FROM fav WHERE username = :query")
    fun searchFavorite(query: String): LiveData<Favorite>

    @Query("DELETE FROM fav")
    fun deleteAll()

    // Content Provider
    @Query("SELECT * FROM fav")
    fun getCursor(): Cursor

    // Widget
    @Query("SELECT * FROM fav")
    fun getItemWidget(): List<Favorite>
}