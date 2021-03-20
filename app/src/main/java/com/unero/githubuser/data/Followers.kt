package com.unero.githubuser.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Data for https://api.github.com/users/{username}/following

@Parcelize
data class Followers(
    val username: String,
    val avatar: String
): Parcelable
