package com.unero.githubuser.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/*
Data for
            https://api.github.com/search/users?q={username}
            https://api.github.com/users/{Username}/followers
            https://api.github.com/users/{Username}/following
 */

@Parcelize
data class User(
    var login: String,
    var avatar_url: String,
    var url: String
): Parcelable
