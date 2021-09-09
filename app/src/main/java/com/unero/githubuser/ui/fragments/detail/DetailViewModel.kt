package com.unero.githubuser.ui.fragments.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.unero.githubuser.data.local.Favorite
import com.unero.githubuser.data.local.FavoriteDatabase
import com.unero.githubuser.data.remote.model.Profile
import com.unero.githubuser.data.remote.model.User
import com.unero.githubuser.data.repository.LocalRepository
import com.unero.githubuser.data.repository.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: LocalRepository

    init {
        val favDao = FavoriteDatabase.getDatabase(application).dao()
        repository = LocalRepository(favDao)
    }

    var errorMessage: MutableLiveData<String> = MutableLiveData()
    var status: MutableLiveData<Boolean> = MutableLiveData()

    // Profile Response
    private var _profile: MutableLiveData<Response<Profile>> = MutableLiveData()
    val profile: LiveData<Response<Profile>> get() = _profile

    // Follow Response
    private var followers: MutableLiveData<Response<List<User>>> = MutableLiveData()
    val listFollower: LiveData<Response<List<User>>> get() = followers

    private var following: MutableLiveData<Response<List<User>>> = MutableLiveData()
    val listFollowing: LiveData<Response<List<User>>> get() = following

    private var _loading: MutableLiveData<Boolean> = MutableLiveData()
    val loading: LiveData<Boolean> get() = _loading

    fun findDetail(username: String){
        viewModelScope.launch(Dispatchers.IO) {
            _loading.postValue(true)
            try {
                _profile.postValue(RemoteRepository.detailUser(username))
                following.postValue(RemoteRepository.followingUser(username))
                followers.postValue(RemoteRepository.followerUser(username))
                _loading.postValue(false)
            } catch (e: Exception) {
                _loading.postValue(false)
                errorMessage.postValue(e.toString())
            }
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