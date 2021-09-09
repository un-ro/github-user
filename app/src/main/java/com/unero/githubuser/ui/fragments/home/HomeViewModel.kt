package com.unero.githubuser.ui.fragments.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.unero.githubuser.data.remote.model.Result
import com.unero.githubuser.data.repository.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {
    // Search Response
    private var _listUser: MutableLiveData<Result> = MutableLiveData()
    val listUser: LiveData<Result> get() = _listUser

    var errorMessage: MutableLiveData<String> = MutableLiveData()

    fun findUser(username: String){
        viewModelScope.launch(Dispatchers.IO) {
            val response = RemoteRepository.findUser(username)
            if (response.isSuccessful) {
                val result = response.body()
                _listUser.postValue(result)
            } else {
                errorMessage.postValue(response.errorBody().toString())
            }
        }
    }
}