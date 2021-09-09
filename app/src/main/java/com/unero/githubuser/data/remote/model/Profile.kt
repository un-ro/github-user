package com.unero.githubuser.data.remote.model

import com.google.gson.annotations.SerializedName

// Data for https://api.github.com/users/{username}

data class Profile(
    @SerializedName("login")
    val username: String,
    val name: String?,
    @SerializedName("html_url")
    val githubUrl: String,
    @SerializedName("avatar_url")
    val avatar: String,
    val company: String?,
    val location: String?,
    @SerializedName("public_repos")
    val repository: Int,
    val followers: Int,
    val following: Int?
)
