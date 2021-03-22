package com.unero.githubuser.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

// Data for https://api.github.com/search/users?q={username}

@Parcelize
data class Result(
    var total_count: Int?,
    var items: ArrayList<User>,
): Parcelable
