package com.unero.githubuser.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Data for https://api.github.com/users/{username}

data class Profile(
    val username: String,
    val name: String,
    val avatar: String,
    val company: String,
    val location: String,
    val joinDate: String,
    val repository: Int
)
