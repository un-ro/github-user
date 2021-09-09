package com.unero.githubuser.data.remote.model

// Data for https://api.github.com/search/users?q={username}
data class Result(
    var total_count: Int?,
    var items: List<User>,
)
