package com.unero.githubuser.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.unero.githubuser.data.local.FavoriteDatabase
import com.unero.githubuser.data.repository.LocalRepository
import kotlinx.coroutines.InternalCoroutinesApi

@InternalCoroutinesApi
class FavoriteProvider : ContentProvider() {

    private lateinit var repository: LocalRepository

    companion object {
        private const val AUTHORITY = "com.unero.githubuser"
        private const val TABLE = "fav"

        private const val FAV = 0
        private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

        init {
            uriMatcher.addURI(AUTHORITY, TABLE, FAV)
        }
    }

    override fun onCreate(): Boolean {
        val favDao = FavoriteDatabase.getDatabase(context as Context).dao()
        repository = LocalRepository(favDao)
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        return when (uriMatcher.match(uri)) {
            FAV -> repository.getCursor()
            else -> null
        }
    }

    override fun getType(uri: Uri): String? = null

    override fun insert(uri: Uri, values: ContentValues?): Uri? = null

    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?): Int = 0

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int = 0
}