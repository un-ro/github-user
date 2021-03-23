package com.unero.githubuser.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.unero.githubuser.data.Repository
import com.unero.githubuser.data.model.Profile
import com.unero.githubuser.data.model.User

class DetailViewModel : ViewModel() {
    private var profileMLD: MutableLiveData<Profile> = MutableLiveData()
    private var followersMLD: MutableLiveData<ArrayList<User>> = MutableLiveData()
    private var followingMLD: MutableLiveData<ArrayList<User>> = MutableLiveData()

    var follower: LiveData<ArrayList<User>>? = null
    var following: LiveData<ArrayList<User>>? = null
    var profile: LiveData<Profile>? = null

    fun fetchProfile(username: String){
        profileMLD = Repository.profile(username)
        profile = profileMLD

    }

    fun fetchFollower(username: String): LiveData<ArrayList<User>> {
        followersMLD = Repository.searchFollowers(username)
        follower = followersMLD
        return followersMLD
    }

    fun refresh(username: String){
        fetchFollower(username)
        fetchFollowing(username)
        fetchProfile(username)
    }

    fun fetchFollowing(username: String): LiveData<ArrayList<User>> {
        followingMLD = Repository.searchFollowing(username)
        following = followingMLD
        return followingMLD
    }
}