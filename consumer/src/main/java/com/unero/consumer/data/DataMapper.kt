package com.unero.consumer.data

import android.database.Cursor
import com.unero.consumer.data.model.Favorite

object DataMapper {
    fun mapCursorToList(cursor: Cursor): List<Favorite> {
        val listFavorite = mutableListOf<Favorite>()

        while (cursor.moveToNext()) {
            listFavorite.add(
                Favorite(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.GithubColumns.ID)),
                    username = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.GithubColumns.USERNAME)),
                    name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.GithubColumns.NAME)),
                    avatar = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.GithubColumns.AVATAR)),
                    followers = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.GithubColumns.FOLLOWERS)),
                    following = cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.GithubColumns.FOLLOWING))
                )
            )
        }

        return listFavorite
    }
}