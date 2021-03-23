package com.unero.githubuser.data.model

// Data for https://api.github.com/users/{username}

data class Profile(
    val login: String?,
    val name: String?,
    val avatar_url: String?,
    var company: String?,
    var location: String?,
    val public_repos: Int?,
    val bio: String?,
    val followers: Int?,
    val following: Int?
)
