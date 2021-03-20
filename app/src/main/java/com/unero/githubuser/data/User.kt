package com.unero.githubuser.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Data for https://api.github.com/search/users?q={username}

@Parcelize
data class User(
    var username: String,
    var avatar: String,
    var link: String
): Parcelable
