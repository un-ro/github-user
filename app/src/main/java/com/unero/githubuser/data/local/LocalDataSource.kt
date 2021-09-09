package com.unero.githubuser.data.local

import android.database.Cursor
import androidx.lifecycle.LiveData
import com.unero.githubuser.data.local.model.Favorite
import com.unero.githubuser.data.local.room.FavoriteDao

class LocalDataSource(private val dao: FavoriteDao) {
    fun listFav(): LiveData<List<Favorite>> = dao.listFav()
    fun searchFavorite(username: String): LiveData<Favorite> = dao.searchFavorite(username)
    fun getCursor(): Cursor = dao.getCursor()
    fun getItemWidget(): List<Favorite> = dao.getItemWidget()

    suspend fun add(item: Favorite) = dao.add(item)
    suspend fun delete(username: String) = dao.delete(username)
    fun deleteAll() = dao.deleteAll()
}