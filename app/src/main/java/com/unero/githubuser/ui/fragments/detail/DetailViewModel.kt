package com.unero.githubuser.ui.fragments.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unero.githubuser.data.Repository
import com.unero.githubuser.data.local.model.Favorite
import com.unero.githubuser.data.remote.model.Profile
import com.unero.githubuser.data.remote.model.User
import com.unero.ungithub.data.utils.APIResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: Repository): ViewModel() {

    var errorMessage: MutableLiveData<String> = MutableLiveData()
    var status: MutableLiveData<Boolean> = MutableLiveData()

    // Profile Response
    private var _profile: MutableLiveData<Profile> = MutableLiveData()
    val profile: LiveData<Profile> get() = _profile

    // Follow Response
    private var followers: MutableLiveData<List<User>> = MutableLiveData()
    val listFollower: LiveData<List<User>> get() = followers

    private var following: MutableLiveData<List<User>> = MutableLiveData()
    val listFollowing: LiveData<List<User>> get() = following

    private var _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> get() = _loading

    fun findDetail(username: String){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            try {
                setUser(username)
                setFollow(username, "follower")
                setFollow(username, "following")
                _loading.postValue(false)
            } catch (e: Exception) {
                _loading.postValue(false)
                errorMessage.postValue(e.toString())
            }
        }
    }

    private suspend fun setFollow(username: String, type: String) {
        when (type) {
            "follower" -> {
                when (val data = repository.findUserFollowers(username)) {
                    is APIResponse.Error -> errorMessage.postValue(data.exception.message)
                    is APIResponse.Success -> followers.postValue(data.data)
                }
            }
            "following" -> {
                when (val data = repository.findUserFollowing(username)) {
                    is APIResponse.Error -> errorMessage.postValue(data.exception.message)
                    is APIResponse.Success -> following.postValue(data.data)
                }
            }
        }
    }

    private suspend fun setUser(username: String) {
        when (val data = repository.findUserDetail(username)) {
            is APIResponse.Error -> errorMessage.postValue(data.exception.message)
            is APIResponse.Success -> _profile.postValue(data.data)
        }
    }

    // Local Data Function
    fun add(favorite: Favorite) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.add(favorite)
        }
    }

    fun delete(query: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(query)
        }
    }

    fun search(query: String): LiveData<Favorite> {
        return repository.searchFavorite(query)
    }

    fun setStatus(boolean: Boolean) {
        status.value = boolean
    }
}