package com.unero.githubuser.data.repository

import android.database.Cursor
import androidx.lifecycle.LiveData
import com.unero.githubuser.data.local.FavoriteDao
import com.unero.githubuser.data.local.Favorite
import kotlinx.coroutines.flow.Flow

class LocalRepository(private val favoriteDao: FavoriteDao) {

    val data: LiveData<List<Favorite>> = favoriteDao.listFav()

    suspend fun add(favorite: Favorite) {
        favoriteDao.add(favorite)
    }

    suspend fun delete(query: String) {
        favoriteDao.delete(query)
    }

    fun search(query: String): Flow<List<Favorite>> {
        return favoriteDao.search(query)
    }

    fun searchOne(query: String): LiveData<Favorite> {
        return favoriteDao.searchOne(query)
    }

    fun deleteAll() {
        favoriteDao.deleteAll()
    }

    fun getCursor(): Cursor {
        return favoriteDao.getCursor()
    }
}