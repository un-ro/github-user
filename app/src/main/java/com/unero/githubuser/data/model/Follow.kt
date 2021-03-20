package com.unero.githubuser.data.model

// Data for https://api.github.com/users/{username}/following

data class Follow(
    val username: String,
    val avatar: String
)
