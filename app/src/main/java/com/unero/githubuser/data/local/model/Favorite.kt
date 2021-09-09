package com.unero.githubuser.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fav")
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val username: String,
    val name: String,
    val avatar: String,
    val followers: Int,
    val following: Int
)
