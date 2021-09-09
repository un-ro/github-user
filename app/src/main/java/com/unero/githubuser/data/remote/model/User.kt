package com.unero.githubuser.data.remote.model

import com.google.gson.annotations.SerializedName

/*
Data for
            https://api.github.com/search/users?q={username}
            https://api.github.com/users/{Username}/followers
            https://api.github.com/users/{Username}/following
 */

data class User(
    @SerializedName("login")
    var username: String,
    @SerializedName("avatar_url")
    var avatar: String,
)
