package com.unero.githubuser.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.internal.synchronized

@Database(entities = [Favorite::class], version = 1, exportSchema = false)
abstract class FavoriteDatabase: RoomDatabase() {

    abstract fun dao(): FavoriteDao

    companion object {
        @Volatile
        private var INSTANCE: FavoriteDatabase? = null

        @InternalCoroutinesApi
        fun getDatabase(context: Context): FavoriteDatabase {
            val temp = INSTANCE
            if (temp != null) {
                return temp
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDatabase::class.java,
                    "favDB"
                ).build()
                INSTANCE = instance
                return INSTANCE as FavoriteDatabase
            }
        }
    }
}