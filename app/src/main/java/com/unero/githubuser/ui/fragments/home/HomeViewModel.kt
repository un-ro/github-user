package com.unero.githubuser.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unero.githubuser.data.remote.model.Result
import com.unero.githubuser.data.repository.RemoteRepository
import kotlinx.coroutines.launch
import retrofit2.Response

class HomeViewModel: ViewModel() {
    // Search Response
    private var _listUser: MutableLiveData<Response<Result>> = MutableLiveData()
    val listUser: LiveData<Response<Result>>
        get() {
            return _listUser
        }

    var errorMessage: MutableLiveData<String> = MutableLiveData()

    fun findUser(username: String){
        viewModelScope.launch {
            try {
                _listUser.value = RemoteRepository.findUser(username)
            } catch (e: Exception) {
                errorMessage.value = e.toString()
            }
        }
    }
}