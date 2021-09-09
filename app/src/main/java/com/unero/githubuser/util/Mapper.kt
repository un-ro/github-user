package com.unero.githubuser.util

import com.unero.githubuser.data.local.Favorite
import com.unero.githubuser.data.remote.model.Profile

object Mapper {
    fun profileToFavorite(profile: Profile): Favorite {
        return Favorite(
            0,
            profile.username,
            if (profile.name.isNullOrEmpty()) profile.username else profile.name,
            profile.avatar,
            profile.followers,
            profile.following!!
        )
    }
}