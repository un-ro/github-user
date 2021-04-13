package com.unero.githubuser.data.repository

import android.database.Cursor
import androidx.lifecycle.LiveData
import com.unero.githubuser.data.local.Favorite
import com.unero.githubuser.data.local.FavoriteDao

class LocalRepository(private val favoriteDao: FavoriteDao) {

    val data: LiveData<List<Favorite>> = favoriteDao.listFav()

    suspend fun add(favorite: Favorite) {
        favoriteDao.add(favorite)
    }

    suspend fun delete(query: String) {
        favoriteDao.delete(query)
    }

    fun searchFavorite(query: String): LiveData<Favorite> {
        return favoriteDao.searchFavorite(query)
    }

    fun deleteAll() {
        favoriteDao.deleteAll()
    }

    // Content Provider
    fun getCursor(): Cursor {
        return favoriteDao.getCursor()
    }
}