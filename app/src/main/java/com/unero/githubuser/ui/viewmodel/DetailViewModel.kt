package com.unero.githubuser.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.unero.githubuser.data.Repository
import com.unero.githubuser.data.model.Profile
import com.unero.githubuser.data.model.User

class DetailViewModel(): ViewModel() {
    var profileMLD: MutableLiveData<Profile> = MutableLiveData()
    var followersMLD: MutableLiveData<ArrayList<User>> = MutableLiveData()
    var followingMLD: MutableLiveData<ArrayList<User>> = MutableLiveData()

    var profile : LiveData<Profile>? = null

    fun fetchProfile(username: String){
        profileMLD = Repository.profile(username)
        profile = profileMLD
    }

    fun fetchFollower(username: String): LiveData<ArrayList<User>> {
        followersMLD = Repository.searchFollowers(username)
        return followersMLD
    }

    fun fetchFollowing(username: String): LiveData<ArrayList<User>> {
        followingMLD = Repository.searchFollowing(username)
        return followingMLD
    }
}