package com.unero.githubuser.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.unero.githubuser.data.local.model.Favorite

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase: RoomDatabase() {
    abstract fun dao(): FavoriteDao
}