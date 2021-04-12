package com.unero.consumer.data

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    private const val AUTHORITY = "com.unero.githubuser"
    private const val SCHEME = "content"
    private const val TABLE_NAME = "fav"

    val contentUri: Uri = Uri.Builder()
        .scheme(SCHEME)
        .authority(AUTHORITY)
        .appendPath(TABLE_NAME)
        .build()

    class GithubColumns: BaseColumns {
        companion object {
            const val ID = "id"
            const val USERNAME = "username"
            const val NAME = "name"
            const val AVATAR = "avatar"
            const val FOLLOWERS = "followers"
            const val FOLLOWING = "following"
        }
    }
}