package com.unero.consumer.data.model

data class Favorite(
    val id: Int,
    val username: String,
    val name: String,
    val avatar: String,
    val followers: Int,
    val following: Int
)
