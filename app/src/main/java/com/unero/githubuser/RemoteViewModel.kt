package com.unero.githubuser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unero.githubuser.data.remote.model.Profile
import com.unero.githubuser.data.remote.model.Result
import com.unero.githubuser.data.remote.model.User
import com.unero.githubuser.data.repository.ApiRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class RemoteViewModel : ViewModel() {

    // User Response
    private var _listUser: MutableLiveData<Response<Result>> = MutableLiveData()
    val listUser: LiveData<Response<Result>>
        get() {
            return _listUser
        }

    // Profile Response
    private var _profile: MutableLiveData<Response<Profile>> = MutableLiveData()
    val profile: LiveData<Response<Profile>>
        get() {
            return _profile
        }

    // Follow Response
    private var followers: MutableLiveData<Response<List<User>>> = MutableLiveData()
    private var following: MutableLiveData<Response<List<User>>> = MutableLiveData()
    val listFollower: LiveData<Response<List<User>>>
        get() {
            return followers
        }
    val listFollowing: LiveData<Response<List<User>>>
        get() {
            return following
        }

    fun findUser(username: String){
        viewModelScope.launch {
            _listUser.value = ApiRepository.findUser(username)
        }
    }

    fun findDetail(username: String){
        viewModelScope.launch {
            _profile.value = ApiRepository.detailUser(username)
        }
    }

    fun listFollow(username: String) {
        viewModelScope.launch {
            following.value = ApiRepository.followingUser(username)
            followers.value = ApiRepository.followerUser(username)
        }
    }
}