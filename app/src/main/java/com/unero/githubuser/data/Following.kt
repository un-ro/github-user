package com.unero.githubuser.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Data for https://api.github.com/users/{username}/followers

@Parcelize
data class Following(
    val username: String,
    val avatar: String
): Parcelable
